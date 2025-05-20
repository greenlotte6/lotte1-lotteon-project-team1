package com.example.lotteon.controller.admin.config;

import com.example.lotteon.dto.admin.PolicyDTO;
import com.example.lotteon.dto.product.CategoryFormDTO;
import com.example.lotteon.dto.product.ProductCategoryDTO;
import com.example.lotteon.dto.product.ProductSubCategoryDTO;
import com.example.lotteon.entity.admin.banner.BannerDocument;
import com.example.lotteon.entity.admin.config.VersionConfig;
import com.example.lotteon.repository.jpa.admin.config.BannerRepository;
import com.example.lotteon.service.admin.BasicConfigService;
import com.example.lotteon.service.admin.CacheService;
import com.example.lotteon.service.admin.PolicyService;
import com.example.lotteon.service.product.category.ProductCategoryService;
import com.example.lotteon.service.product.category.ProductSubCategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class BasicConfigController {

  @Value("${lotteon.upload.banner.path}")
  private String uploadPath;

  @Value("${lotteon.upload.prefix.banner}")
  private String uploadPrefix;

  private final ProductCategoryService categoryService;
  private final ProductSubCategoryService subCategoryService;
  private final BannerRepository bannerRepository;
  private final CacheService cacheService;
  private final BasicConfigService service;
  private final PolicyService policyService;

  @GetMapping("/basic")
  public String basic(Model model, HttpServletRequest request) {
    return "/admin/config/basic";
  }

  @GetMapping("/banner")
  public String banner(@RequestParam(value = "position", defaultValue = "MAIN") String position,
      Model model) {
    List<BannerDocument> bannerDocs = bannerRepository.getBannerByPosition(position);
    model.addAttribute("banners", bannerDocs);

    return switch (position.toLowerCase()) {
      case "login" -> "/admin/config/banner/login";
      case "mypage" -> "/admin/config/banner/mypage";
      case "slider" -> "/admin/config/banner/main-slider";
      case "detail" -> "/admin/config/banner/product-detail";
      default -> "/admin/config/banner/main-top";
    };
  }

  @PostMapping("/banner/update")
  public String banner(@RequestParam("_id") String _id,
      @RequestParam("status") String newStatus,
      @RequestParam("position") String position,
      HttpServletResponse response) throws IOException {
    if (newStatus.equals("active")) { //배너의 상태를 active로 변경
      BannerDocument targetDoc = bannerRepository.getBanner(_id);
      LocalDateTime startTime = targetDoc.getStart();
      LocalDateTime expireTime = targetDoc.getExpiration();

      //이미 만료된 배너를 활성화 시키고자 하거나 시작 날짜 이전에 활성화 시키고자 하는 경우
      if (expireTime.isBefore(LocalDateTime.now()) || startTime.isAfter(LocalDateTime.now())) {
        return "redirect:/admin/config/banner?position=" + position;
      }
    } else if (newStatus.equals("inactive")) {//배너의 상태를 inactive로 변경
      BannerDocument targetDoc = bannerRepository.getBanner(_id);
      LocalDateTime startTime = targetDoc.getStart();
      if (LocalDateTime.now().isBefore(startTime)) { //시작 날짜 이전에 배너를 비활성화 시키고자 하는 경우
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return "redirect:/admin/config/banner?position=" + position;
      }
    }
    bannerRepository.changeStatus(_id, newStatus); //MongoDB의 배너 데이터 상태 변경
    BannerDocument targetDoc = bannerRepository.getBanner(_id); //변경된 배너 데이터 MongoDB에서 조회
    if (targetDoc.getStatus().equals("active")) { //변경된 상태가 active인 경우
      cacheService.cacheBanner(targetDoc);
    } else { // 변경된 상태가 inactive인 경우, 캐시에서 삭제
      cacheService.invalidateBannerCache(targetDoc.get_id(), targetDoc.getPosition());
    }
    return "redirect:/admin/config/banner?position=" + position;
  }

  private File getUploadPath(MultipartFile image) {
    String originalName = image.getOriginalFilename();
    int extensionIndex = originalName.lastIndexOf(".");
    String extension = originalName.substring(extensionIndex);
    String uuidName = UUID.randomUUID().toString();
    String destinationPath = uploadPath + "/" + uuidName + extension;
    return new File(destinationPath);
  }

  @PostMapping("/banner/register")
  public String registerBanner(MultipartFile image, BannerDocument banner) throws IOException {
    File dest = getUploadPath(image);
    image.transferTo(dest);
    String url = uploadPrefix + dest.getName();

    banner.setId("admin::banner");
    banner.setLocation(url);
    bannerRepository.save(banner);
    return "redirect:/admin/config/banner?position=" + banner.getPosition();
  }

  @PostMapping("/banner/delete")
  public String deleteBanner(@RequestParam("_ids") List<String> _ids) {
    _ids.forEach(id -> {
      bannerRepository.delete(id);
      cacheService.invalidateBannerCache(id);
    });
    return "redirect:/admin/config/banner";
  }

  @GetMapping("/policy")
  public String policy(Model model) {
    List<PolicyDTO> policies = policyService.list();
    model.addAttribute("policies", policies);
    return "/admin/config/policy";
  }

  @PostMapping("/policy/edit")
  public String edit(PolicyDTO policy) {
    policyService.save(policy);
    return "redirect:/admin/config/policy";
  }

  @GetMapping("/category")
  public String category(Model model) {
    Map<ProductCategoryDTO, List<ProductSubCategoryDTO>> map = categoryService.listWithSubCategories();
    List<ProductSubCategoryDTO> flatSubCategories = map.values().stream().flatMap(List::stream)
        .collect(
            Collectors.toList());

    model.addAttribute("flatSubCategories", flatSubCategories);
    model.addAttribute("map", map);
    return "/admin/config/category";
  }

  @PostMapping("/category")
  public String edit(CategoryFormDTO form) {
    categoryService.update(form.getCategories());
    subCategoryService.update(form.getSubCategories());
    cacheService.invalidateCategoryCache();
    cacheService.invalidateSubCategoryCache();
    return "redirect:/admin/config/category";
  }

  @GetMapping("/version")
  public String version(Model model) {
    return "/admin/config/version";
  }

  @PostMapping("/version/register")
  public String version(VersionConfig config) {
    config.setId("basic_config::version");
    config.setCreatedAt(new Date());

    service.updateLatestVersion(config);
    cacheService.invalidateCache();
    return "redirect:/admin/config/version";
  }
}
