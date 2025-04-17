package com.example.lotteon.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMainController {

  @GetMapping(value = {"", "/", "/index"})
  public String index() {
    return "/admin/index";
  }

}
