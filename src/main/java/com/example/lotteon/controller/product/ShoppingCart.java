package com.example.lotteon.controller.product;

import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.service.product.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ShoppingCart {

    private final ShoppingCartService shoppingCartService;

//    @PostMapping("/product/cart/add")
//    public String addCart(@RequestParam("id") int id) {
//        shoppingCartService.addCart(id);
//        return "redirect:/product/cart/view";
//    }
//
//    @GetMapping("/product/cart/view")
//    public String viewCart(HttpSession session, Model model) {
//        List<ProductDTO> cart = (List<ProductDTO>) session.getAttribute("cart");
//        if (cart == null) {
//            cart = new ArrayList<>();
//        }
//
//        model.addAttribute("cart", cart);
//        return "/product/shoppingCart";
//    }

    // 장바구니에 상품 추가
    @PostMapping("/product/cart/add")
    public String addCart(@RequestParam("id") int productId) {
        shoppingCartService.addCart(productId);  // 상품 아이디로 장바구니에 추가
        return "redirect:/product/cart/view"; // 장바구니 보기 페이지로 리다이렉트
    }


}