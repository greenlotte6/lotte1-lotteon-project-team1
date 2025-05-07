package com.example.lotteon.controller.api;

import com.example.lotteon.entity.order.Order;
import com.example.lotteon.service.order.OrderService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/order")
public class OrderManagementApiController {

  private final OrderService service;
  private final Gson gson;

  @GetMapping("/deliver")
  public ResponseEntity<String> deliver(@RequestParam(name = "id") String orderNumber) {
    Order order = service.searchByOrderNumber(orderNumber);

    if (order == null) {
      return ResponseEntity.notFound().build();
    }

    String json = gson.toJson(order);
    return ResponseEntity.ok(json);
  }
}
