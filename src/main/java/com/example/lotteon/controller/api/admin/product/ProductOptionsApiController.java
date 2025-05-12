package com.example.lotteon.controller.api.admin.product;

import com.example.lotteon.service.product.options.ProductOptionsService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/product/options")
public class ProductOptionsApiController {

  private final ProductOptionsService service;
  private final Gson gson;

  @GetMapping("/id")
  public ResponseEntity<String> id() {
    int latestId = service.getLatestId();
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("latestId", latestId);
    String json = gson.toJson(jsonObject);
    return ResponseEntity.ok(json);
  }
}
