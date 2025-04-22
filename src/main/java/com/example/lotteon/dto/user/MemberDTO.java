package com.example.lotteon.dto.user;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

  private static final String[] GENDERS = {"m", "f"};

  private UserCompositeKeyDTO userCompositeKey;
  private String name;
  private String gender;
  private LocalDate recentLoginDate;
  private String description;

}
