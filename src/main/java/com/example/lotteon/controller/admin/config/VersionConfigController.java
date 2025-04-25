package com.example.lotteon.controller.admin.config;

import com.example.lotteon.entity.admin.config.VersionConfig;
import com.example.lotteon.service.admin.BasicConfigService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/config/version")
@RequiredArgsConstructor
public class VersionConfigController {

  private final BasicConfigService configService;

  @GetMapping("/")
  public String version(Model model) {
    List<VersionConfig> versions = configService.getAllVersions();
    model.addAttribute("versions", versions);
    return "/admin/config/version";
  }
}
