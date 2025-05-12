package com.example.lotteon.entity.order;

import com.example.lotteon.entity.product.Product;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
public class OrderItem {

  @Id
  @SerializedName("id")
  private int id;

  @JoinColumn(name = "order_number")
  @OneToOne(optional = false)
  @SerializedName("order")
  private Order order;

  @JoinColumn(name = "product_id")
  @ManyToOne(optional = false)
  @SerializedName("product")
  private Product product;

  @Column(name = "amount")
  @SerializedName("amount")
  private int amount;

  @Column(name = "total_price")
  private int totalPrice;

}