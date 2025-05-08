package com.example.lotteon.controller.admin.seller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/sales")
public class AdminSalesController {

  @GetMapping("/list")
  public String list() {

    return "/admin/shop/sales";
  }
}
