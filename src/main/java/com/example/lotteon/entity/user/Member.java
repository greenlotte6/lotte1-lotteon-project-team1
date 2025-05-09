package com.example.lotteon.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

  private static String STATUS_NORMAL = "normal";
  private static String STATUS_STOPPED = "stopped";
  private static String STATUS_DORMANT = "dormant";
  private static String STATUS_WITHDRAWED = "withdrawed";
  private static String LEVEL_FAMILY = "family";
  private static String LEVEL_SILVER = "silver";
  private static String LEVEL_GOLD = "gold";
  private static String LEVEL_VIP = "vip";
  private static String LEVEL_VVIP = "vvip";
  private static final String[] GENDERS = {"m", "f"};

  @Id
  private MemberId memberId;

  @Column(name = "name")
  private String name;

  @Column(name = "gender")
  private String gender;

  @Column(name = "recent_login_date")
  private LocalDate recentLoginDate;

  @Column(name = "description")
  private String description;

  @Column(name = "status")
  private String status;

  @Column(name = "level")
  private String level;

  @Column(name = "birth_date")
  private Date birthDate;
}
