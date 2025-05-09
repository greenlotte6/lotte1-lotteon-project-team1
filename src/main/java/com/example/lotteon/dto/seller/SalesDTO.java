package com.example.lotteon.dto.seller;

import com.example.lotteon.entity.order.Order;
import com.example.lotteon.entity.seller.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesDTO {

  private int id;
  private Seller seller;
  private Order order;
}
