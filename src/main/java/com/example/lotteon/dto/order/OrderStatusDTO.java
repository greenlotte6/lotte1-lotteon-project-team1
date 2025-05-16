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
  public static final String STATUS_PREPARE_DELIVERY = "prepare_delivery";
  public static final String STATUS_ON_DELIVERY = "on_delivery";
  public static final String STATUS_DELIVERED = "delivered";
  public static final String STATUS_PURCHASE_CONFIRMED = "purchase_confirmed";
  public static final String STATUS_CANCEL_REQUESTED = "cancel_requested";
  public static final String STATUS_CANCELLED = "cancelled";
  public static final String STATUS_REFUND_REQUESTED = "refund_requested";
  public static final String STATUS_REFUNDED = "refunded";
  public static final String STATUS_EXCHANGE_REQUESTED = "exchange_requested";
  public static final String STATUS_EXCHANGED = "exchanged";

  public static final int STATUS_PAYMENT_WAITING_ID = 1;
  public static final int STATUS_PAID_ID = 2;
  public static final int STATUS_PREPARE_DELIVERY_ID = 3;
  public static final int STATUS_ON_DELIVERY_ID = 4;
  public static final int STATUS_DELIVERED_ID = 5;
  public static final int STATUS_PURCHASE_CONFIRMED_ID = 6;
  public static final int STATUS_CANCEL_REQUESTED_ID = 7;
  public static final int STATUS_CANCELLED_ID = 8;
  public static final int STATUS_REFUND_REQUESTED_ID = 9;
  public static final int STATUS_REFUNDED_ID = 10;
  public static final int STATUS_EXCHANGE_REQUESTED_ID = 11;
  public static final int STATUS_EXCHANGED_ID = 12;

  public static final int[] IDS = {STATUS_PAYMENT_WAITING_ID, STATUS_PAID_ID,
      STATUS_PREPARE_DELIVERY_ID,
      STATUS_ON_DELIVERY_ID, STATUS_DELIVERED_ID, STATUS_PURCHASE_CONFIRMED_ID,
      STATUS_CANCEL_REQUESTED_ID, STATUS_CANCELLED_ID, STATUS_REFUND_REQUESTED_ID,
      STATUS_REFUNDED_ID, STATUS_EXCHANGE_REQUESTED_ID, STATUS_EXCHANGED_ID};

  public static final String[] STATUSES = {STATUS_PAID, STATUS_PURCHASE_CONFIRMED,
      STATUS_CANCEL_REQUESTED,
      STATUS_CANCELLED, STATUS_REFUND_REQUESTED, STATUS_REFUNDED, STATUS_EXCHANGE_REQUESTED};

  private int id;
  private String name;
}
