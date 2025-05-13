package com.example.lotteon.dto.order;

import com.example.lotteon.entity.order.OrderItem;
import com.example.lotteon.entity.product.ProductOptions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemOptionsDTO {

  private OrderItem orderItem;
  private ProductOptions productOptions;
}
