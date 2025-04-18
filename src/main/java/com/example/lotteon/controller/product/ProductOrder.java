package com.example.lotteon.controller.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ProductOrder {

    @GetMapping("/product/order")
    public String productOrder() {
        return "/product/proOrder";
    }
}
