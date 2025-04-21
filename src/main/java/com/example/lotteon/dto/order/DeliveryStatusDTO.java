package com.example.lotteon.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatusDTO {

  private static final String[] STATUSES = {"ready", "on_delivery", "delivered", "cancel_requested",
      "cancelled"};
  private int id;
  private String status;
}
