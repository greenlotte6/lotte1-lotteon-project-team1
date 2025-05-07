package com.example.lotteon.entity.order;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

  @Id
  @SerializedName("id")
  private int id;

  @JoinColumn(name = "order_number")
  @OneToOne
  @SerializedName("order")
  private Order order;

  @Column(name = "delivery_number")
  @SerializedName("delivery_number")
  private String deliveryNumber;

  @Column(name = "description")
  @SerializedName("description")
  private String description;

  @JoinColumn(name = "delivery_company_id")
  @ManyToOne
  @SerializedName("delivery_company")
  private DeliveryCompany deliveryCompany;

  @JoinColumn(name = "status_id")
  @ManyToOne
  @SerializedName("status")
  private DeliveryStatus status;
}
