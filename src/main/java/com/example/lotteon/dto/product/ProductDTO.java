package com.example.lotteon.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

  private int id;
  private ProductCategoryDTO category;
  private ProductSubCategoryDTO subCategory;
  private ProductOptionsDTO options;
  private String name;
  private String description;
  private String company;
  private int price;
  private int point;
  private int discountRate;
  private int stock;
  private int deliveryFee;
  private ProductImageDTO image;
  private String status;
  private boolean isVatFree;
  private String businessClassification;
  private boolean receiptIssuable;
  private String origin;
}
