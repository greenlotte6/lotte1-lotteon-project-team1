package com.example.lotteon.dto.order;

import com.example.lotteon.dto.product.ProductDTO;
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
public class OrderSheetRequestForm {

  private int amount;
  private List<ProductOptionsDTO> options;
  private List<ProductDTO> products;
  private int totalPrice;
}
