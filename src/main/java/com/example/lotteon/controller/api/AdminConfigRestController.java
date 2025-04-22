package com.example.lotteon.controller.api;

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
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/admin/config")
@RequiredArgsConstructor
public class AdminConfigRestController {

  private final Gson gson;
  private final AdminConfigService service;

  @PutMapping("/basic/site")
  public ResponseEntity<String> postBasic(@RequestBody Site doc) {
    log.info(doc.toString());

    //ConfigDocument doc = service.update(docDTO);

    return new ResponseEntity<>(gson.toJson(doc), HttpStatus.OK);
  }
}
