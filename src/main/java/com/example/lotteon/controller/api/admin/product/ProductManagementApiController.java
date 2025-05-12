package com.example.lotteon.controller.api.admin.product;

import com.example.lotteon.service.product.ProductService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/product")
public class ProductManagementApiController {

  private final ProductService service;
  private final Gson gson;

  private ResponseEntity<String> ok() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("status", "success");
    String body = gson.toJson(jsonObject);
    return ResponseEntity.ok(body);
  }

  @PutMapping(value = {"", "/"})
  public ResponseEntity<String> updateStatus(@RequestBody(required = false) List<Integer> ids,
      @RequestParam(name = "id", required = false) Integer id,
      @RequestParam(name = "status") String status) {
    if (ids != null && !ids.isEmpty()) {
      for (Integer i : ids) {
        service.updateStatusById(i, status);
      }
    }

    if (id != null && id > 0) {
      service.updateStatusById(id, status);
    }
    return ok();
  }
}
