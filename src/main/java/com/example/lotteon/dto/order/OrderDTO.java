package com.example.lotteon.dto.order;

import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.dto.user.MemberDTO;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

  private int id;
  private MemberDTO member;
  private DeliveryDTO delivery;
  private ProductDTO product;
  private int count;
  private int priceTotal;
  private String payment;
  private OrderStatusDTO status;
  private LocalDate orderDate;

}
