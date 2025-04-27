package com.example.lotteon.dto.user;

import java.time.LocalDate;
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

  public static final String ROLE_MEMBER = "member";
  public static final String ROLE_ADMIN = "admin";
  public static final String ROLE_SELLER = "seller";
  public static final String[] ROLES = {ROLE_MEMBER, ROLE_ADMIN, ROLE_SELLER};

  private String id;
  private String password;
  private String email;
  private String contact;
  private String zip;
  private String address;
  private String addressDetail;
  private String role;
  private LocalDate registerDate;
}
