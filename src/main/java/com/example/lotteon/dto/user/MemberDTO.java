package com.example.lotteon.dto.user;

import java.time.LocalDate;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

  private static final String[] GENDERS = {"m", "f"};

  private MemberIdDTO memberId;
  private String name;
  private String gender;
  private LocalDate recentLoginDate;
  private String description;
  private String status;
  private String level;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date birthDate;
}
