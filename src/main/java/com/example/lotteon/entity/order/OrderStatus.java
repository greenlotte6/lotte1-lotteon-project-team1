package com.example.lotteon.entity.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus {

  private static final String[] STATUSES = {"paid", "on_delivery", "delivered",
      "purchase_confirmed", "cancel_requested", "cancelled", "refund_requested", "refunded"};

  @Id
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;
}
