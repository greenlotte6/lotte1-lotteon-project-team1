package com.example.lotteon.entity.seller;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seller")
public class Seller {

  @Id
  private SellerCompositeKey sellerCompositeKey;

  @Column(name = "ceo")
  private String ceo;

  @Column(name = "company_name")
  private String companyName;

  @Column(name = "seller_number")
  private String sellerNumber;

  @Column(name = "fax")
  private String fax;

}
