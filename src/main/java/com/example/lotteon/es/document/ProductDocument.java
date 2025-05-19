package com.example.lotteon.es.document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@Document(indexName = "product")
@NoArgsConstructor
@AllArgsConstructor
public class ProductDocument {

  @Id
  private int id;

  private int categoryId;
  private int subCategoryId;
  private String name;
  private String description;
  private String sellerId;
  private int price;
  private int point;
  private int discountRate;
  private int stock;
  private int deliveryFee;
  private int imageId;
  private String status;
  private boolean isVatFree;
  private String businessClassification;
  private boolean receiptIssuable;
  private String origin;
  private String quality;
}
