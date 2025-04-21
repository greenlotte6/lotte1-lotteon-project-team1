package com.example.lotteon.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

  private static final String[] GENDERS = {"m", "f"};

  @Id
  private UserCompositeKey userCompositeKey;

  @Column(name = "name")
  private String name;

  @Column(name = "gender")
  private String gender;

  @Column(name = "recent_login_date")
  private LocalDate recentLoginDate;

  @Column(name = "description")
  private String description;
}
