package com.example.lotteon.dto.order;

import com.example.lotteon.dto.user.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {

  private OrderDTO order;
  private MemberDTO member;
  private String deliveryNumber;
  private String recipientName;
  private String recipientContact;
  private String recipientZip;
  private String recipientAddress;
  private String recipientAddressDetail;
  private String description;
  private DeliveryCompanyDTO deliveryCompany;
  private DeliveryStatusDTO status;
}
