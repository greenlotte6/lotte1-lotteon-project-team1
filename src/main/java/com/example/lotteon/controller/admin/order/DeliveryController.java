package com.example.lotteon.controller.admin.order;

import com.example.lotteon.dto.order.DeliveryDTO;
import com.example.lotteon.dto.order.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/delivery")
public class DeliveryController {

  @PostMapping("register")
  public String register(OrderDTO order, @RequestParam("deliveryNumber") String deliveryNumber) {
    //TODO 1. Update order's status to 3(prepare_status)
    //TODO 2. Build DeliveryDTO with order and deliveryNumber
    //TODO 3. Insert into `lotteon`.`delivery`
    DeliveryDTO delivery = DeliveryDTO.builder()
        .build();
    return "redirect:/admin/order/list";
  }
}
