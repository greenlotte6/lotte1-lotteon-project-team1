package com.example.lotteon.dto.seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {

  private SellerIdDTO sellerId;
  private String ceo;
  private String companyName;
  private String sellerNumber;
  private String fax;
}
