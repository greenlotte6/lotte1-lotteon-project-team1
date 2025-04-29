package com.example.lotteon.dto.point;

import com.example.lotteon.dto.user.MemberDTO;
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
  private MemberDTO memberId;
  private int amount;
  private String description;
  private Date issuedDate;
  private int total;
}
