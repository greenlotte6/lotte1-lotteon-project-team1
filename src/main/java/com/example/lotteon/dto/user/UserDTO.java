package com.example.lotteon.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDTO {

  private static final String[] ROLES = {"member", "seller", "admin"};

  private String id;
  private String password;
  private String email;
  private String contact;
  private String zip;
  private String address;
  private String addressDetail;
  private String role;
  private String registerDate;
}
