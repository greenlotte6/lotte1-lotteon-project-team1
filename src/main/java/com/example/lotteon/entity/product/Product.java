package com.example.lotteon.entity.product;

import com.example.lotteon.converter.BooleanToEnumStringConverter;
import com.example.lotteon.entity.seller.Seller;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
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
  @Column(name = "id")
  private int id;

  @JoinColumn(name = "category_id")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private ProductCategory category;

  @JoinColumn(name = "subcategory_id")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private ProductSubCategory subCategory;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumns({
      @JoinColumn(name = "seller_user_id", referencedColumnName = "user_id"),
      @JoinColumn(name = "seller_business_number", referencedColumnName = "business_number")
  })
  private Seller seller;

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
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  private ProductImage image;

  @Column(name = "status")
  private String status;

  @Column(name = "is_vat_free")
  @Convert(converter = BooleanToEnumStringConverter.class)
  private boolean isVatFree;

  @Column(name = "business_class")
  private String businessClassification;

  @Column(name = "receipt_issuable")
  @Convert(converter = BooleanToEnumStringConverter.class)
  private boolean receiptIssuable;

  @Column(name = "origin")
  private String origin;

  @Column(name = "quality")
  private String quality;
}
