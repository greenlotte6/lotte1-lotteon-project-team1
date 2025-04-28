package com.example.lotteon.controller.product;
import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductList {

    private final ProductService productService;

    @GetMapping("/product/list")
    public String productList(Model model) {
       List<ProductDTO> proDTOs = productService.proList();

       log.info("proDTOs: {}", proDTOs);
        model.addAttribute("products", proDTOs);
       return "/product/proList";
    }

}
