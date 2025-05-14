package com.example.lotteon.controller.api.admin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/config/banner")
public class BannerRestController {

  @GetMapping("/info")
  public ResponseEntity<String> info(@RequestParam("_id") String _id) {
    return ResponseEntity.ok("{'status':'OK'}");
  }
}
