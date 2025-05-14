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
public class ProductCategoryDTO {

  @SerializedName("id")
  private int id;

  @SerializedName("name")
  private String name;

  @SerializedName("sequence")
  private int sequence;
}
