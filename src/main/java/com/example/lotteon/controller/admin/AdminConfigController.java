package com.example.lotteon.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/config")
public class AdminConfigController {

  @GetMapping("/basic")
  public String basic() {
    return "/admin/config/basic";
  }

  @GetMapping("/banner")
  public String banner() {
    return "/admin/config/banner/main-top";
  }

  @GetMapping("/policy")
  public String policy() {
    return "/admin/config/policy";
  }

  @GetMapping("/version")
  public String version() {
    return "/admin/config/version";
  }
}
