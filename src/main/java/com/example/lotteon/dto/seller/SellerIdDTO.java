package com.example.lotteon.dto.seller;

import com.example.lotteon.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerIdDTO {

  private String businessNumber;
  private UserDTO user;
}
