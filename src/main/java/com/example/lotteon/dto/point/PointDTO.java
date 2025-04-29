package com.example.lotteon.dto.point;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointDTO {

  private int id;
  private String memberId;
  private int amount;
  private String description;
  private Date issuedDate;
  private int total;
}
