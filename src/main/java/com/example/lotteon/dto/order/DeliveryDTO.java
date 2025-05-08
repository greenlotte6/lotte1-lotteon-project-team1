package com.example.lotteon.dto.order;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {

  private int id;
  private OrderDTO order;
  private String deliveryNumber;
  private DeliveryCompanyDTO deliveryCompany;
  private Date receiptDate;
}
