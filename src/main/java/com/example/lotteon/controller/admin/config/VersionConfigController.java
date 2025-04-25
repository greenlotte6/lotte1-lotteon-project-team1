package com.example.lotteon.controller.admin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/config/version")
@RequiredArgsConstructor
public class VersionConfigController {

  @GetMapping("/")
  public String version() {

    return "/admin/config/version";
  }
}
