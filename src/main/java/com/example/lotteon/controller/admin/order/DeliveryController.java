package com.example.lotteon.controller.admin.order;

import com.example.lotteon.dto.order.DeliveryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/delivery")
public class DeliveryController {

  @PostMapping("register")
  public String register(DeliveryDTO delivery) {
    //TODO 1. Update order's status to 3(prepare_delivery)
    //TODO 2. Build DeliveryDTO with order and deliveryNumber
    //TODO 3. Insert into `lotteon`.`delivery`
    return "redirect:/admin/order/list";
  }
}
