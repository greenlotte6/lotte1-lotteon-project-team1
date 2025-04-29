package com.example.lotteon.entity.point;

import com.example.lotteon.entity.user.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Point {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(name = "amount")
  private int amount;

  @Column(name = "description")
  private String description;

  @Column(name = "issued_date")
  @CreationTimestamp
  private Date issuedDate;

  @Column(name = "total")
  private int total;
}
