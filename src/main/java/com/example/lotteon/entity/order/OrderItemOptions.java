package com.example.lotteon.entity.order;

import com.example.lotteon.entity.product.ProductOptions;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class OrderItemOptions {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @JoinColumn(name = "order_item_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private OrderItem orderItem;

  @JoinColumn(name = "product_options_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private ProductOptions productOptions;
}
