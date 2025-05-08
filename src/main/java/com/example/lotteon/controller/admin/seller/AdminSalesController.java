package com.example.lotteon.controller.admin.seller;

import com.example.lotteon.service.seller.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/sales")
public class AdminSalesController {

  private final SalesService service;

  @GetMapping("/list")
  public String list() {
    service.findAll();
    return "/admin/shop/sales";
  }
}
