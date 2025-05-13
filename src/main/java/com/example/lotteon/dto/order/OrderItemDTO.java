package com.example.lotteon.dto.order;

import com.example.lotteon.dto.product.ProductDTO;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

  @SerializedName("id")
  private int id;
  @SerializedName("order")
  private OrderDTO order;
  @SerializedName("product")
  private ProductDTO product;
  @SerializedName("amount")
  private int amount;
  @SerializedName("totalPrice")
  private int totalPrice;
}
