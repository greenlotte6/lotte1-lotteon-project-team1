package com.example.lotteon.entity.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryCompany {

  @Id
  @Column(name = "id")
  private int id;

  @Column(name = "company_name")
  private String companyName;
}
