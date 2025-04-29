package com.example.lotteon.controller.admin.product;

import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.entity.product.ProductOptions;
import com.example.lotteon.service.product.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class ProductManagementController {

  private final ProductService service;

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
    //TODO: Select All categories and subcategories and add them to model(04/30 01:09)
    Product product = service.getById(id);
    List<ProductOptions> options = service.getOptions(id);
    model.addAttribute("product", product);
    model.addAttribute("options", options);
    return "/admin/product/edit";
  }

  @PostMapping("/edit")
  public String edit(@RequestParam("id") int id,
      ProductDTO product) {
    service.edit(id, product);
    return "redirect:/admin/product/list";
  }
}
