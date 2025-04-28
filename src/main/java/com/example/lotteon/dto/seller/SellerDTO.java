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

  public static final String STATUS_RUN = "run";
  public static final String STATUS_STOP = "stopped";
  public static final String STATUS_READY = "ready";

  private SellerIdDTO sellerId;
  private String ceo;
  private String companyName;
  private String sellerNumber;
  private String fax;
  private String status;
}
