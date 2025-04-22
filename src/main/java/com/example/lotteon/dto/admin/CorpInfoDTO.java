package com.example.lotteon.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CorpInfoDTO {

  private String name;
  private String ceo;
  private String businessNumber;
  private String sellerNumber;
  private String address;
  private String addressDetail;

}
