package com.example.lotteon.dto.order;

import com.example.lotteon.entity.order.Order;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderWrapper {

  private Order order;

  @Column(name = "numberOfOrderedProducts")
  private long numberOfOrderedProducts;

  @Column(name = "totalPrice")
  private int totalPrice;
}
