package com.example.lotteon.controller.product;

import com.example.lotteon.dto.product.SessionCartDTO;
import com.example.lotteon.entity.product.CartId;
import com.example.lotteon.repository.product.ProductRepository;
import com.example.lotteon.service.product.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ShoppingCart {

  private final ShoppingCartService shoppingCartService;
  private final ProductRepository productRepository;

  @PostMapping("/product/cart/add")
  public String addCart(@RequestParam("id") int productId,
      @RequestParam("quantity") int quantity,
      HttpSession session) {
    return "redirect:/product/cart/view";
  }

  @GetMapping("/product/cart/view")
  public String viewCart(HttpSession session, Model model) {
    return "/product/shoppingCart";
  }

  @PostMapping("/cart/deleteSelected")
  public String deleteSelectedCartItems(@RequestParam("productIds") List<Integer> productIds,
      HttpSession session) {
    String memberId = (String) session.getAttribute("loginUserId");

    List<CartId> compositeIds = productIds.stream()
        .map(pid -> new CartId(memberId, pid))
        .toList();

    shoppingCartService.deleteByCompositeIds(compositeIds);
    return "redirect:/product/cart/view";
  }

  // ✔ 공통 요약정보 계산 메서드
  public static void applyCartSummary(List<SessionCartDTO> cartItems, Model model) {
  }
}
