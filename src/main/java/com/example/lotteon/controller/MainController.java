package com.example.lotteon.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

  @Value("${spring.application.version}")
  private String version;

  @GetMapping(value = {"/", "/index"})
  public String index(Model model) {
    model.addAttribute("version", version);
    return "/index";
  }
}
