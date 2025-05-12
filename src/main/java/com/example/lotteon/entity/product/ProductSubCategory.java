package com.example.lotteon.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_subcategory")
public class ProductSubCategory {

  @Id
  @Column(name = "id")
  private String id;

  @JoinColumn(name = "category_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private ProductCategory category;

  @Column(name = "name")
  private String name;

  @Column(name = "sequence")
  private int sequence;
}
