package com.example.lotteon.dto.product;

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
public class CartDTO {

  private int id;
  private MemberDTO member;
  private ProductDTO product;
  private int amount;
  private LocalDate registerDate;
}