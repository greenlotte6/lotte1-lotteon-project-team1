package com.example.lotteon.controller;

import com.example.lotteon.service.admin.BasicConfigService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

  private final BasicConfigService basicConfigService;

  @GetMapping(value = {"/", "/index"})
  public String index(Model model, HttpServletRequest request) {
    return "/index";
  }

}
