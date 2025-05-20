package com.example.lotteon.controller.admin.product;

import com.example.lotteon.dto.product.ProductCategoryDTO;
import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.dto.product.ProductImageDTO;
import com.example.lotteon.dto.product.ProductOptionsDTO;
import com.example.lotteon.dto.product.ProductSubCategoryDTO;
import com.example.lotteon.dto.product.ProductWrapperDTO;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.entity.product.ProductOptions;
import com.example.lotteon.service.admin.CacheService;
import com.example.lotteon.service.product.ProductService;
import com.example.lotteon.service.product.category.ProductCategoryService;
import com.example.lotteon.service.product.category.ProductSubCategoryService;
import com.example.lotteon.service.product.image.ImagePersistenceService;
import com.example.lotteon.service.product.options.ProductOptionsService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
  private final ImagePersistenceService uploader;
  private final ProductOptionsService optionsService;
  private final ProductCategoryService categoryService;
  private final ProductSubCategoryService subCategoryService;
  private final CacheService cacheService;
  private final ModelMapper modelMapper;

  private int createNewId(int categoryId) {
    String latestIdField =
        "000" + (service.getLatestIdAndIncrement() % 10000);//ex)0001;
    String registeredYear = String.valueOf(LocalDate.now().getYear());//ex) 2025
    String categoryIdStr = "0" + (categoryId);//ex) 01
    return Integer.parseInt(registeredYear + categoryIdStr + latestIdField);
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
  public String register(Model model) {
    List<ProductCategoryDTO> categories = categoryService.getAll();
    List<ProductSubCategoryDTO> subCategories = subCategoryService.getAll();
    model.addAttribute("categories", categories);
    model.addAttribute("subCategories", subCategories);
    return "/admin/product/register";
  }

  @PostMapping("/register")
  public String register(@ModelAttribute ProductWrapperDTO wrapper,
      @RequestParam Map<String, MultipartFile> imageMap,
      HttpServletResponse response) throws IOException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetails details = (UserDetails) auth.getPrincipal();
    ProductDTO incomingProduct = wrapper.getProduct();
    List<ProductOptionsDTO> incomingOptions = wrapper.getOptions();
    //int createdId = createNewId(incomingProduct.getCategory().getId());//최신 product id 생성
    int createdId = service.getLatestIdAndIncrement() + 1;
    ProductImageDTO insertedImage = uploader.uploadAndInsert(imageMap);
    incomingProduct.setImage(insertedImage);
    incomingProduct.setId(createdId); // POST 요청의 product에 생성된 id 초기화
    incomingProduct.setStatus("on_sale"); // 상품 상태 == 판매중
    service.register(details.getUsername(), incomingProduct); // POST 요청된 product INSERT
    optionsService.save(wrapper.getProduct().getId(),
        incomingOptions); // POSt 요청된 product options INSERT
    return "redirect:/admin/product/list";
  }

  @GetMapping("/edit")
  public String edit(@RequestParam(name = "id") int id, Model model) {
    List<ProductCategoryDTO> categories = categoryService.getAll();
    List<ProductSubCategoryDTO> cachedSubCategories = subCategoryService.getAll();

    Product product = service.getById(id);
    List<ProductOptions> options = service.getOptions(id);
    model.addAttribute("product", product);
    model.addAttribute("categories", categories);
    model.addAttribute("subCategories", cachedSubCategories);
    model.addAttribute("options", options);
    return "/admin/product/edit";
  }

  @PostMapping(value = "/edit")
  public String edit(@RequestParam("id") int id,
      @ModelAttribute ProductWrapperDTO wrapper,
      @RequestParam Map<String, MultipartFile> imageMap,
      HttpServletResponse response) {

    //업로드된 새로운 상품 이미지 저장
    try {
      optionsService.save(id, wrapper.getOptions()); // 상품 옵션 업데이트
      int imageId = wrapper.getProduct().getImage().getId();
      uploader.uploadAndUpdate(imageId, imageMap);
      service.edit(id, wrapper.getProduct());
    } catch (IOException e) {
      log.error(e.getMessage());
      response.setStatus(500);
      return "";
    }
    return "redirect:/admin/product/list";
  }
}
