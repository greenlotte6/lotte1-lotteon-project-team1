package com.example.lotteon.dto.order;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class DeliveryOrderItemWrapper {

  @SerializedName("delivery")
  private DeliveryDTO delivery;

  @SerializedName("orderItems")
  private List<OrderItemDTO> orderItems;
}
