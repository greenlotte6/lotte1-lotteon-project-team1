package com.example.lotteon.controller.api;

import com.example.lotteon.dto.admin.ConfigDTO;
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

  @PutMapping(value = {"/basic", "/basic/"})
  public ResponseEntity<String> postBasic(@RequestBody ConfigDTO docDTO) {
    log.info(docDTO.toString());

    //ConfigDocument doc = service.update(docDTO);

    return new ResponseEntity<>(gson.toJson(docDTO), HttpStatus.OK);
  }
}
