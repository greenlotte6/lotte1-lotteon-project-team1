package com.example.lotteon.controller.admin.config;

import com.example.lotteon.dto.admin.PolicyDTO;
import com.example.lotteon.dto.product.CategoryFormDTO;
import com.example.lotteon.dto.product.ProductCategoryDTO;
import com.example.lotteon.dto.product.ProductSubCategoryDTO;
import com.example.lotteon.entity.admin.banner.BannerDocument;
import com.example.lotteon.entity.admin.config.VersionConfig;
import com.example.lotteon.repository.admin.config.BannerRepository;
import com.example.lotteon.service.admin.BasicConfigService;
import com.example.lotteon.service.admin.CacheService;
import com.example.lotteon.service.admin.PolicyService;
import com.example.lotteon.service.product.category.ProductCategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class BasicConfigController {

  private final ProductCategoryService categoryService;
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

    //return switch (position.toLowerCase()) {
    //  case "login" -> "/admin/config/banner/login";
    //  case "mypage" -> "/admin/config/banner/mypage";
    //  case "slider" -> "/admin/config/banner/main-slider";
    //  case "product" -> "/admin/config/banner/product";
    //  default -> "/admin/config/banner/main-top";
    //};
    return "/admin/config/banner/main-top";
  }

  @PostMapping("/banner/update")
  public String banner(@RequestParam("_id") String _id,
      @RequestParam("status") String newStatus,
      @RequestParam("position") String position,
      HttpServletResponse response) throws IOException {
    if (newStatus.equals("active")) {
      BannerDocument targetDoc = bannerRepository.getBanner(_id);
      LocalDateTime startTime = targetDoc.getStart();
      LocalDateTime expireTime = targetDoc.getExpiration();

      //이미 만료된 배너를 활성화 시키고자 하거나 시작 날짜 이전에 활성화 시키고자 하는 경우
      if (expireTime.isBefore(LocalDateTime.now()) || startTime.isAfter(LocalDateTime.now())) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return "redirect:/admin/config/banner?position=" + position;
      }
    } else if (newStatus.equals("inactive")) {
      BannerDocument targetDoc = bannerRepository.getBanner(_id);
      LocalDateTime startTime = targetDoc.getStart();
      if (LocalDateTime.now().isBefore(startTime)) { //시작 날짜 이전에 배너를 비활성화 시키고자 하는 경우
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return "redirect:/admin/config/banner?position=" + position;
      }
    }
    bannerRepository.changeStatus(_id, newStatus);
    return "redirect:/admin/config/banner?position=" + position;
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
    model.addAttribute("map", map);
    return "/admin/config/category";
  }

  @PostMapping("/category")
  public String edit(CategoryFormDTO form) {
    categoryService.update(form);
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
