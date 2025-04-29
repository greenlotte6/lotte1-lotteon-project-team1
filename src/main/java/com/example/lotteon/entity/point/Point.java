package com.example.lotteon.entity.point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

  private String memberId;

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
