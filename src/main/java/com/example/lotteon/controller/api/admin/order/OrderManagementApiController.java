package com.example.lotteon.controller.api.admin.order;

import com.example.lotteon.dto.order.OrderItemDTO;
import com.example.lotteon.entity.order.Order;
import com.example.lotteon.service.order.OrderService;
import com.google.gson.Gson;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

  @GetMapping("/ship")
  public ResponseEntity<String> deliver(@RequestParam(name = "id") String orderNumber) {
    Order order = service.searchByOrderNumber(orderNumber);

    if (order == null) {
      return ResponseEntity.notFound().build();
    }

    String json = gson.toJson(order);
    return ResponseEntity.ok(json);
  }

  @GetMapping("/detail")
  public ResponseEntity<String> detail(@RequestParam(name = "id") String orderNumber) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetails details = (UserDetails) auth.getPrincipal();

    boolean isSeller = details.getAuthorities().stream()
        .anyMatch(authority -> authority.getAuthority().equals("ROLE_SELLER"));
    List<OrderItemDTO> items = null;

    if (isSeller) {
      String currentUserId = details.getUsername();
      items = service.getOrderDetail(currentUserId, orderNumber);
    } else {
      items = service.getOrderDetail(orderNumber);
    }

    if (items.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    String json = gson.toJson(items);
    return ResponseEntity.ok(json);
  }
}
