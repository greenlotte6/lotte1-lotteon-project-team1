package com.example.lotteon.controller.api.admin.delivery;

import com.example.lotteon.dto.order.DeliveryOrderItemWrapper;
import com.example.lotteon.service.delivery.DeliveryService;
import com.google.gson.Gson;
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
@RequestMapping("/api/admin/delivery")
public class DeliveryManagementApiController {

  private final DeliveryService service;
  private final Gson gson;

  private UserDetails getUserDetails() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (UserDetails) authentication.getPrincipal();
  }

  @GetMapping("/search")
  public ResponseEntity<String> search(@RequestParam(name = "id") String deliveryNumber) {
    UserDetails userDetails = getUserDetails();

    boolean isSeller = userDetails.getAuthorities().stream()
        .anyMatch(authority -> authority.getAuthority().equals("ROLE_SELLER"));
    boolean isAdmin = userDetails.getAuthorities().stream()
        .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

    DeliveryOrderItemWrapper delivery = null;

    if (isSeller) {
      String currentSellerId = userDetails.getUsername();
      delivery = service.getDetailOf(currentSellerId, deliveryNumber);
    } else {
      delivery = service.getDetailOf(deliveryNumber);
    }

    String json = gson.toJson(delivery);

    return ResponseEntity.ok(json);
  }
}
