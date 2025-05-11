package com.example.lotteon.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSubCategoryDTO {

  private int id;
  private ProductCategoryDTO category;
  private String name;
}
