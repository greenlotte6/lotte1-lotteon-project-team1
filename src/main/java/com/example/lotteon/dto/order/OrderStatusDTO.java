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

  public static final String STATUS_PAYMENT_WAITING = "payment_waiting";
  public static final String STATUS_PAID = "paid";
  public static final String STATUS_PURCHASE_CONFIRMED = "purchase_confirmed";
  public static final String STATUS_CANCEL_REQUESTED = "cancel_requested";
  public static final String STATUS_CANCELLED = "cancelled";
  public static final String STATUS_REFUND_REQUESTED = "refund_requested";
  public static final String STATUS_REFUNDED = "refunded";
  public static final String STATUS_EXCHANGE_REQUESTED = "exchange_requested";

  public static final String[] STATUSES = {STATUS_PAID, STATUS_PURCHASE_CONFIRMED,
      STATUS_CANCEL_REQUESTED,
      STATUS_CANCELLED, STATUS_REFUND_REQUESTED, STATUS_REFUNDED, STATUS_EXCHANGE_REQUESTED};

  private int id;
  private String name;
}
