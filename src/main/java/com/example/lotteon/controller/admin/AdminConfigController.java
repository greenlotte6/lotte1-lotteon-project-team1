package com.example.lotteon.controller.admin;

import com.example.lotteon.entity.admin.config.ConfigDocument;
import com.example.lotteon.service.admin.AdminConfigService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class AdminConfigController {

  private final AdminConfigService service;

  @GetMapping("/basic")
  public String basic(Model model, HttpServletRequest request) {
    ConfigDocument config = (ConfigDocument) request.getAttribute("cachedConfig");
    if (config == null) { //캐싱된 기본설정 데이터가 없는 경우
      config = service.getConfig(); // MongoDB에서 조회 후 캐싱
    }
    model.addAttribute("config", config);
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

  @GetMapping("/category")
  public String category() {
    return "/admin/config/category";
  }

  @GetMapping("/version")
  public String version() {
    return "/admin/config/version";
  }
}
