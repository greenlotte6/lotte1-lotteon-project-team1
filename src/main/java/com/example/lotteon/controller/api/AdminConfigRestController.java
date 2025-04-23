package com.example.lotteon.controller.api;

import com.example.lotteon.entity.admin.config.CorpInfo;
import com.example.lotteon.entity.admin.config.CustomerServiceInfo;
import com.example.lotteon.entity.admin.config.Site;
import com.example.lotteon.service.admin.AdminConfigService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/admin/config/basic")
@RequiredArgsConstructor
public class AdminConfigRestController {

  private final Gson gson;
  private final AdminConfigService service;

  @PutMapping("/site")
  public ResponseEntity<String> postBasic(@RequestBody Site config,
      @RequestParam String key) {
    log.info("PUT request for {} with {}", key, config.toString());

    service.updateSite(config);

    return new ResponseEntity<>(gson.toJson(config), HttpStatus.OK);
  }

  @PutMapping("/corp")
  public ResponseEntity<String> updateCorpInfo(@RequestBody CorpInfo config,
      @RequestParam String key) {
    log.info("PUT request for {} with {}", key, config.toString());
    service.updateCorpInfo(config);
    return new ResponseEntity<>("", HttpStatus.OK);
  }

  @PutMapping("/cs")
  public ResponseEntity<String> updateCsInfo(@RequestBody CustomerServiceInfo config,
      @RequestParam String key) {
    log.info("PUT request for {} with {}", key, config.toString());
    service.updateCsInfo(config);
    return new ResponseEntity<>("", HttpStatus.OK);
  }

  @PutMapping("/copyright")
  public ResponseEntity<String> updateCopyright(@RequestBody String copyright,
      @RequestParam String key) {
    log.info("PUT request for {} with {}", key, copyright);
    service.updateCopyright(key, copyright);
    return new ResponseEntity<>("", HttpStatus.OK);
  }
}
