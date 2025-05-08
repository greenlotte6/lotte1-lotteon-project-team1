package com.example.lotteon.controller.admin.order;

import com.example.lotteon.dto.order.DeliveryDTO;
import com.example.lotteon.dto.order.OrderStatusDTO;
import com.example.lotteon.service.delivery.DeliveryService;
import com.example.lotteon.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/delivery")
public class DeliveryController {

  private final DeliveryService deliService;
  private final OrderService orderService;

  @PostMapping("register")
  public String register(DeliveryDTO delivery) {
    String orderNumber = delivery.getOrder().getOrderNumber();
    OrderStatusDTO status = OrderStatusDTO.builder()
        .id(3)
        .name(OrderStatusDTO.STATUS_PREPARE_DELIVERY)
        .build();
    orderService.updateStatusByOrderNumber(orderNumber, status);
    deliService.save(delivery);
    return "redirect:/admin/order/list";
  }
}
