package com.example.lotteon.entity.user;

import com.example.lotteon.entity.point.Point;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
  private UserCompositeKey userCompositeKey;

  @JoinColumn(name = "point_id")
  @OneToOne(fetch = FetchType.LAZY)
  private Point point;

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
}
