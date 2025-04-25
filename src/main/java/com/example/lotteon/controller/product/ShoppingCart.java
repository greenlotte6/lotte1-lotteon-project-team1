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

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
public class ShoppingCart {

    private final ShoppingCartService shoppingService;

    @GetMapping("/product/cart")
    public String shoppingCart(@RequestParam("id") int id , Model model) {

        ProductDTO proDTO = shoppingService.getCart(id);
        model.addAttribute("proDTO", proDTO);

        return "/product/shoppingCart";
    }

   // @GetMapping("/product/cart")
    //public String shoppingCart(@RequestParam("id") int id, Model model) {


      //  return "/product/shoppingCart";
   // }

    //@PostMapping("/product/cart")
    //public String shoppingCart(@RequestParam("id") int id, HttpSession session) {
     //   shoppingService.addCart(id);
      //  return "redirect:/product/cart";
   // }
}
