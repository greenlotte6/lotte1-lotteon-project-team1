package com.example.lotteon.controller.product;

import com.example.lotteon.dto.order.OrderItemDTO;
import com.example.lotteon.dto.order.OrderSheet;
import com.example.lotteon.dto.product.CartDTO;
import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.dto.product.SessionCartDTO;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.service.product.CartService;
import com.example.lotteon.service.product.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;
  private final ProductService productService;
  private final ModelMapper mapper;

  @GetMapping("/cart")
  public String viewCart(HttpSession session, Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()
        || auth instanceof AnonymousAuthenticationToken) {//비회원 유저가 장바구니 페이지에 접속했을 경우
      SessionCartDTO sessionCart = (SessionCartDTO) session.getAttribute("sessionCart");

      if (sessionCart == null) { //장바구니에 상품이 없을 경우
        model.addAttribute("cartItems", new ArrayList<>());
      } else {
        model.addAttribute("cartItems", sessionCart.getCartItems());
      }

    } else { //회원이 장바구니 페이지에 접속했을 경우
      UserDetails details = (UserDetails) auth.getPrincipal();
      String memberId = details.getUsername();
      SessionCartDTO sessionCart = (SessionCartDTO) session.getAttribute("sessionCart");
      if (sessionCart != null) {// 비회원 상태에서 장바구니에 상품을 담았을 경우
        // 세션 장바구니를 DB에 저장
        List<CartDTO> cartItems = sessionCart.getCartItems();
        cartService.addAll(memberId, cartItems);
        session.removeAttribute("sessionCart");
      }

      // 비회원 상태에서 장바구니에 상품을 담지 않았을 경우, 단순 조회
      List<CartDTO> cartItems = cartService.listForCurrentMember(memberId);
      model.addAttribute("cartItems", cartItems);
    }

    return "/product/shoppingCart";
  }

  @PostMapping("/cart")
  public String addCart(HttpSession session, CartDTO cartItem) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()
        || auth instanceof AnonymousAuthenticationToken) {//비회원 유저가 장바구니에 상품을 담는 경우
      SessionCartDTO sessionCart = (SessionCartDTO) session.getAttribute("sessionCart");

      //장바구니에 담을 상품에 관한 데이터 DB 조회 후 cartItem.product 초기화
      Product targetEntity = productService.getById(cartItem.getProduct().getId());
      ProductDTO targetProduct = mapper.map(targetEntity, ProductDTO.class);
      cartItem.setProduct(targetProduct);

      if (sessionCart == null) { // 세션 장바구니에 처음으로 상품을 담는 경우
        List<CartDTO> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        SessionCartDTO newSessionCart = new SessionCartDTO(cartItems);
        session.setAttribute("sessionCart", newSessionCart);
      } else {// 세션 장바구니에 처음으로 상품을 담는 것이 아닌 경우
        sessionCart.getCartItems().add(cartItem);
      }
    } else {//회원이 장바구니에 상품을 담는 경우
      UserDetails details = (UserDetails) auth.getPrincipal();
      cartService.add(details.getUsername(), cartItem);
    }
    return "redirect:/cart";
  }

  @PostMapping("/cart/order")
  public String orderCart(HttpSession session, HttpServletResponse response) throws IOException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()
        || auth instanceof AnonymousAuthenticationToken) {  // 비회원 사용자가 접근했을 경우
      return "redirect:/login";
    } else {
      OrderSheet orderSheet = OrderSheet.builder().build();
      UserDetails userDetails = (UserDetails) auth.getPrincipal();
      String memberId = userDetails.getUsername();
      List<CartDTO> cartItems = cartService.listForCurrentMember(memberId);
      List<OrderItemDTO> orderItems = new ArrayList<>();
      for (CartDTO cartItem : cartItems) {
        ProductDTO currProduct = cartItem.getProduct();
        OrderItemDTO newOrderItem = OrderItemDTO.builder()
            .product(currProduct)
            .amount(cartItem.getAmount())
            .build();
        orderItems.add(newOrderItem);
      }
      orderSheet.setOrderItems(orderItems);
      orderSheet.setOptions(new ArrayList<>()); //TODO Delete options from order sheet or use them
      session.setAttribute("orderSheet", orderSheet);
    }
    return "redirect:/order/sheet?src=cart";
  }

  @PostMapping("/cart/delete")
  public String deleteSelectedCartItems(@RequestParam("productIds") List<Integer> productIds,
      HttpSession session) {
    return "redirect:/product/cart/view";
  }
}
