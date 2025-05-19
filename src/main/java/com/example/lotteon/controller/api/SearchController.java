package com.example.lotteon.controller.api;

import com.example.lotteon.service.es.SearchProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

  private final SearchProductService searchService;

  @GetMapping("/search")
  public ResponseEntity<String> search(String keyword) {
    String json = searchService.search(keyword);
    return ResponseEntity.ok(json);
  }
}
