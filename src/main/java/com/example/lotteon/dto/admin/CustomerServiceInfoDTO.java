package com.example.lotteon.dto.admin;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerServiceInfoDTO {

  private String contact;

  private String officeHour;

  private String email;

  private String disputeContact;
}
