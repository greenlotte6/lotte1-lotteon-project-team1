package com.example.lotteon.dto.order;

import com.example.lotteon.dto.product.ProductOptionsDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSheet {

  private OrderDTO order;
  private List<OrderItemDTO> orderItems;
  private List<ProductOptionsDTO> options;
}
