package com.example.lotteon.dto.product;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSubCategoryDTO {

  @SerializedName("id")
  private int id;

  @SerializedName("category")
  private ProductCategoryDTO category;

  @SerializedName("name")
  private String name;

  @SerializedName("sequence")
  private int sequence;
}
