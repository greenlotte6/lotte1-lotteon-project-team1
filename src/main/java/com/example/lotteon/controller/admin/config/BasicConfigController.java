package com.example.lotteon.controller.admin.config;

import com.example.lotteon.dto.admin.PolicyDTO;
import com.example.lotteon.dto.product.CategoryFormDTO;
import com.example.lotteon.dto.product.ProductCategoryDTO;
import com.example.lotteon.dto.product.ProductSubCategoryDTO;
import com.example.lotteon.entity.admin.config.VersionConfig;
import com.example.lotteon.service.admin.BasicConfigService;
import com.example.lotteon.service.admin.CacheService;
import com.example.lotteon.service.admin.PolicyService;
import com.example.lotteon.service.product.category.ProductCategoryService;
import com.example.lotteon.service.product.category.ProductSubCategoryService;
import jakarta.servlet.http.HttpServletRequest;
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

@Slf4j
@Controller
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class BasicConfigController {

  private final ProductCategoryService categoryService;
  private final ProductSubCategoryService subCategoryService;
  private final CacheService cacheService;
  private final BasicConfigService service;
  private final PolicyService policyService;

  @GetMapping("/basic")
  public String basic(Model model, HttpServletRequest request) {
    return "/admin/config/basic";
  }

  @GetMapping("/banner")
  public String banner() {
    return "/admin/config/banner/main-top";
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
