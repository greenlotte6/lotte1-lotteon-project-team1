package com.example.lotteon.controller.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ProductOrderRs {

    @GetMapping("/product/orderRs")
    public String productOrderRs() {
        return "/product/proorderRs";
    }
}
