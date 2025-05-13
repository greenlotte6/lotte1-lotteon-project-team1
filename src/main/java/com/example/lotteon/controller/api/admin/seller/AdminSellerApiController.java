package com.example.lotteon.controller.api.admin.seller;

import com.example.lotteon.service.seller.SellerService;
import com.example.lotteon.service.user.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/seller")
public class AdminSellerApiController {

  private final UserService userService;
  private final SellerService service;
  private final Gson gson;

  private ResponseEntity<String> ok() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("status", "ok");
    return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(jsonObject));
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> delete(@RequestBody List<String> businessNumbers) {
    service.deleteByBusinessNumbers(businessNumbers);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("status", "ok");
    //TODO: Delete from user instead of seller;
    return ok();
  }

  @PutMapping("/manage")
  public ResponseEntity<String> manage(@RequestParam("businessNumber") String businessNumber,
      @RequestParam("status") String newStatus) {
    service.updateStatus(businessNumber, newStatus);
    return ok();
  }

}
