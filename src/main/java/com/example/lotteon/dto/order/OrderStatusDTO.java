package com.example.lotteon.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDTO {

  private static final String[] STATUSES = {"paid", "on_delivery", "delivered",
      "purchase_confirmed", "cancel_requested", "cancelled", "refund_requested", "refunded"};

  private int id;
  private String name;
}
