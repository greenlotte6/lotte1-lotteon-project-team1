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
public class DeliveryStatus {

  private static final String[] STATUSES = {"ready", "on_delivery", "delivered", "cancel_requested",
      "cancelled"};

  @Id
  @Column(name = "id")
  private int id;

  @Column(name = "status")
  private String status;
}
