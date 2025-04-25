package com.example.lotteon.controller.api;

import com.example.lotteon.entity.admin.config.VersionConfig;
import com.example.lotteon.exception.NoDocumentFoundException;
import com.example.lotteon.service.admin.BasicConfigService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: 1. 상세 버튼을 누르면 해당 버튼이 속해있는 tr에서 '버전 이름' 가져오고 api로 요청 보내기
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/config/version")
@RequiredArgsConstructor
public class VersionConfigRestController {

  private final BasicConfigService service;
  private final Gson gson;

  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> search(@RequestParam("version") String name) {
    String json = "";
    try {
      VersionConfig config = service.getVersionByName(name);
      json = gson.toJson(config);
    } catch (NoDocumentFoundException e) {
      log.error(e.getMessage(), e);
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(json, HttpStatus.OK);
  }

}
