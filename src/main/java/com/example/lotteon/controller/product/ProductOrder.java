package com.example.lotteon.controller.product;

import com.example.lotteon.dto.product.SessionCartDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.product.Cart;
import com.example.lotteon.service.product.ShoppingCartService;
import com.example.lotteon.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.example.lotteon.controller.product.ShoppingCart.applyCartSummary;

@Controller
@RequiredArgsConstructor
public class ProductOrder {

    private final ShoppingCartService shoppingCartService;
    private final UserService userService; // 🔥 추가

    @GetMapping("/product/order")
    public String productOrder(HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("loginUserId");
        if (memberId == null) return "redirect:/member/login";

        // ✅ 로그인한 사용자 정보 가져오기
        UserDTO user = userService.getUserInfo(memberId);
        model.addAttribute("user", user);

        // ✅ 장바구니 정보 처리
        List<Cart> cartItems = shoppingCartService.getCartItems(memberId);
        List<SessionCartDTO> cartDTOs = cartItems.stream()
                .map(cart -> new SessionCartDTO(
                        cart.getProduct().getId(),
                        cart.getProduct().getName(),
                        cart.getPrice(),
                        cart.getProduct().getDiscountRate(),
                        cart.getProduct().getPoint(),
                        cart.getDil(),
                        cart.getQuantity(),
                        cart.getTotalPrice()
                ))
                .toList();

        model.addAttribute("cart", cartDTOs);
        applyCartSummary(cartDTOs, model);

        return "/product/proOrder";
    }
}
