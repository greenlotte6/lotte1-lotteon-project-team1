package com.example.lotteon.dto.order;

import com.example.lotteon.dto.user.MemberDTO;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

  private String orderNumber;
  private MemberDTO member;
  private String payment;
  private String recipientName;
  private String recipientContact;
  private String recipientZip;
  private String recipientAddress;
  private String recipientAddressDetail;
  private String description;
  private OrderStatusDTO status;
  private LocalDate orderDate;

}
