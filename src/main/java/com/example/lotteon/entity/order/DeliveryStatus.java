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

  public static final String STATUS_READY = "ready";
  public static final String STATUS_ON_DELIVERY = "on_delivery";
  public static final String STATUS_DELIVERED = "delivered";
  public static final String STATUS_CANCEL_REQUESTED = "cancel_requested";
  public static final String STATUS_CANCELLED = "cancelled";
  public static final String[] STATUSES = {STATUS_READY, STATUS_ON_DELIVERY, STATUS_DELIVERED,
      STATUS_CANCEL_REQUESTED, STATUS_CANCELLED};

  @Id
  @Column(name = "id")
  private int id;

  @Column(name = "status")
  private String status;
}
