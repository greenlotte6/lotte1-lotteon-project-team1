package com.example.lotteon.dto.product;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryFormDTO {

  private List<ProductCategoryDTO> categories;
  private List<ProductSubCategoryDTO> subCategories;
}
