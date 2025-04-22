package com.example.lotteon.controller.admin;

import com.example.lotteon.entity.admin.config.ConfigDocument;
import com.example.lotteon.repository.admin.AdminConfigRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class AdminConfigController {

  private final AdminConfigRepository repo;

  @Value("${lotteon.data.mongodb.admin.config.id}")
  private String docId;

  @GetMapping("/basic")
  public String basic(Model model) {
    Optional<ConfigDocument> opt = repo.findById(docId);

    ConfigDocument doc = null;
    doc = opt.orElseGet(ConfigDocument::new); // DB에 데이터가 존재하지 않는다면 모든 속성값이 null인 객체 생성
    model.addAttribute("config", doc);
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
