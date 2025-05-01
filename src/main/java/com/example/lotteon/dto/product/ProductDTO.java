package com.example.lotteon.dto.product;

import com.example.lotteon.dto.seller.SellerDTO;
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
  private String name;
  private String description;
  private SellerDTO seller;
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
  private String quality;
}
