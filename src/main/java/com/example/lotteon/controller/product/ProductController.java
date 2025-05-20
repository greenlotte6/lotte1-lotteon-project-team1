package com.example.lotteon.controller.product;

import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.entity.coupon.Coupon;
import com.example.lotteon.entity.product.ProductOptions;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.repository.jpa.coupon.CouponHistoryRepository;
import com.example.lotteon.repository.jpa.coupon.CouponRepository;
import com.example.lotteon.service.coupon.CouponHistoryService;
import com.example.lotteon.service.product.ProductDetailService;
import com.example.lotteon.service.product.ProductService;
import com.example.lotteon.service.user.MemberService;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;
  private final ProductDetailService productDetailService;
  private final MemberService memberService;
  private final CouponHistoryRepository couponHistoryRepository;
  private final CouponRepository couponRepository;
  private final CouponHistoryService couponHistoryService;

  @GetMapping("/product/list")
  public String productList(
          @RequestParam(required = false) String filter,
          @RequestParam(required = false) String sort,
          @RequestParam(required = false) String subcategoryId,
          Model model) {

    List<ProductDTO> products;

    if (subcategoryId != null) {
      if ("sales".equalsIgnoreCase(filter)) {
        products = productService.proListBySubCategoryIdSortedBySales(subcategoryId);
      } else if ("price".equalsIgnoreCase(filter)) {
        products = productService.proListBySubCategoryIdSortedByPrice(subcategoryId, sort);
      } else {
        products = productService.proListBySubCategoryId(subcategoryId);
      }
    } else {
      if ("sales".equalsIgnoreCase(filter)) {
        products = productService.proListSortedBySales();
      } else if ("price".equalsIgnoreCase(filter)) {
        products = productService.proListSortedByPrice(sort);
      } else {
        products = productService.proList();
      }
    }

    model.addAttribute("products", products);
    model.addAttribute("subcategoryId", subcategoryId); // 뷰에서 유지되도록 전달
    return "/product/proList";
  }

  @GetMapping("/product/detail")
  public String productDetail(@RequestParam("id") int productId, Model model) {

    ProductDTO proDTOs = productDetailService.productDetail(productId);
    List<ProductOptions> options = productService.getOptions(productId);
    Map<String, List<ProductOptions>> optionsMap = new LinkedHashMap<>();
    for (ProductOptions option : options) {
      String name = option.getOption();
      List<ProductOptions> optionsWithSameName =
          options.stream().filter(opt -> opt.getOption().equals(name)).toList();
      optionsMap.put(name, optionsWithSameName);
    }

    model.addAttribute("prodtos", proDTOs);
    model.addAttribute("options", optionsMap);

    return "/product/proDetail";
  }

  @PostMapping("/product/detail/give-coupon")
  public String giveCoupon(@RequestParam("id") int id, Authentication authentication,
      RedirectAttributes redirectAttributes) {
    String username = authentication.getName();
    Member member = memberService.getMemberEntityByUserId(username);  // 이걸로 수정

    try {
      Coupon coupon = couponRepository.findById(201)
          .orElseThrow(() -> new IllegalStateException("쿠폰을 찾을 수 없습니다."));

      boolean couponAlreadyGiven = couponHistoryRepository.existsByMemberAndCoupon(member,
          coupon);  // OK
      if (couponAlreadyGiven) {
        redirectAttributes.addFlashAttribute("error", "이미 쿠폰을 받으셨습니다.");
      } else {
        couponHistoryService.couponHistoryRegister(member, coupon);
        redirectAttributes.addFlashAttribute("message", "쿠폰이 성공적으로 지급되었습니다!");
      }
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", "쿠폰 지급에 실패했습니다: " + e.getMessage());
    }

    return "redirect:/product/detail?id=" + id;
  }
}
