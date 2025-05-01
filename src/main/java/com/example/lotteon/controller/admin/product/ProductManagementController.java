package com.example.lotteon.controller.admin.product;

import com.example.lotteon.dto.product.ProductWrapperDTO;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.entity.product.ProductCategory;
import com.example.lotteon.entity.product.ProductOptions;
import com.example.lotteon.entity.product.ProductSubCategory;
import com.example.lotteon.service.product.ProductService;
import com.example.lotteon.service.product.category.ProductCategoryService;
import com.example.lotteon.service.product.category.ProductSubCategoryService;
import com.example.lotteon.service.product.image.ProductImageService;
import com.example.lotteon.service.product.options.ProductOptionsService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class ProductManagementController {

  @Value("${lotteon.upload.product.path}")
  private String uploadPath;

  private final ProductService service;
  private final ProductImageService imageService;
  private final ProductOptionsService optionsService;
  private final ProductCategoryService categoryService;
  private final ProductSubCategoryService subCategoryService;

  private void updateImage(int imageId, String filename, String entryKey) {
    switch (entryKey) {
      case "listThumbnail": {
        imageService.updateListThumbnail(imageId, filename);
        break;
      }
      case "mainThumbnail": {
        imageService.updateMainThumbnail(imageId, filename);
        break;
      }
      case "detailThumbnail": {
        imageService.updateDetailThumbnail(imageId, filename);
        break;
      }
      case "detailImage": {
        imageService.updateDetailImage(imageId, filename);
        break;
      }
    }
  }

  private void doUpload(int imageId, Map<String, MultipartFile> imageMap) throws IOException {
    for (Map.Entry<String, MultipartFile> entry : imageMap.entrySet()) {
      String key = entry.getKey();
      MultipartFile image = entry.getValue();

      if (!image.isEmpty()) {//업로드된 이미지 파일이 있을 때에만 저장 및 UPDATE 수행
        String originalName = image.getOriginalFilename();
        int extensionIndex = originalName.lastIndexOf(".");
        String extension = originalName.substring(extensionIndex);
        String uuidName = UUID.randomUUID().toString();
        String destinationPath = uploadPath + "/" + uuidName + extension;
        File dest = new File(destinationPath);
        image.transferTo(dest);

        updateImage(imageId, "/upload/product/" + uuidName + extension, key);
      }
    }
  }

  @GetMapping("/list")
  public String list(
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      Model model) {
    Pageable pageable = PageRequest.of(page - 1, size);
    Page<Product> pages = service.getAll(pageable);

    model.addAttribute("currentPage", page);
    model.addAttribute("pages", pages);
    return "/admin/product/product";
  }

  @GetMapping("/search")
  public String search(
      @RequestParam("filter") String filter,
      @RequestParam("keyword") String keyword,
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      Model model
  ) {
    Pageable pageable = PageRequest.of(page - 1, size);
    Page<Product> pages = null;
    switch (filter) {
      case "name": {
        pages = service.getByName(keyword, pageable);
        break;
      }
      case "id": {
        pages = service.getPagesById(Integer.parseInt(keyword), pageable);
        break;
      }
      case "company": {
        pages = service.getByCompany(keyword, pageable);
        break;
      }
    }

    model.addAttribute("currentPage", page);
    model.addAttribute("pages", pages);
    return "/admin/product/product";
  }

  @GetMapping("/register")
  public String enroll() {
    return "/admin/product/register";
  }

  @PostMapping("/register")
  public String enroll(@ModelAttribute Product product) {
    return "redirect:/admin/product/list";
  }

  @GetMapping("/edit")
  public String edit(@RequestParam(name = "id") int id, Model model) {
    List<ProductCategory> categories = categoryService.getAll();
    List<ProductSubCategory> subCategories = subCategoryService.getAll();
    Product product = service.getById(id);
    List<ProductOptions> options = service.getOptions(id);
    model.addAttribute("product", product);
    model.addAttribute("categories", categories);
    model.addAttribute("subCategories", subCategories);
    model.addAttribute("options", options);
    return "/admin/product/edit";
  }

  @PostMapping(value = "/edit")
  public String edit(@RequestParam("id") int id,
      @ModelAttribute ProductWrapperDTO wrapper,
      @RequestParam Map<String, MultipartFile> imageMap) {

    //업로드된 새로운 상품 이미지 저장
    try {
      optionsService.save(id, wrapper.getOptions()); // 상품 옵션 업데이트
      int imageId = wrapper.getProduct().getImage().getId();
      doUpload(imageId, imageMap);
      service.edit(id, wrapper.getProduct());
    } catch (IOException e) {
      log.error(e.getMessage());
      return "";
    }
    return "redirect:/admin/product/list";
  }
}
