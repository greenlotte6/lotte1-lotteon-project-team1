package com.example.lotteon.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  @Id
  private int id;

  @JoinColumn(name = "category_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private ProductCategory category;

  @JoinColumn(name = "subcategory_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private ProductSubCategory subCategory;

  @JoinColumn(name = "options_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private ProductOptions options;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "company")
  private String company;

  @Column(name = "price")
  private int price;

  @Column(name = "point")
  private int point;

  @Column(name = "discount_rate")
  private int discountRate;

  @Column(name = "stock")
  private int stock;

  @Column(name = "delivery_fee")
  private int deliveryFee;

  @JoinColumn(name = "image_id")
  @OneToOne(fetch = FetchType.LAZY)
  private ProductImage image;

  @Column(name = "status")
  private String status;

  @Column(name = "is_vat_free")
  private boolean isVatFree;

  @Column(name = "business_class")
  private String businessClassification;

  @Column(name = "receipt_issuable")
  private boolean receiptIssuable;

  @Column(name = "origin")
  private String origin;
}
