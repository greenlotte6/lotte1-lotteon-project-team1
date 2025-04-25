package com.example.lotteon.controller.product;

import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.service.product.ProductDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Slf4j
@Controller
public class ProductDetail {

    private final ProductDetailService productDetailService;


    @GetMapping("/product/detail")
    public String productDetail(@RequestParam("id") int id, Model model) {

        ProductDTO proDTOs = productDetailService.productDetail(id);

        model.addAttribute("prodtos", proDTOs);

        return "/product/proDetail";
    }

}
