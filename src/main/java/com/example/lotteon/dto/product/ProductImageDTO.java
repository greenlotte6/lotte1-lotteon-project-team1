package com.example.lotteon.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDTO {

  private int id;
  private String listThumbnailLocation;
  private String mainThumbnailLocation;
  private String detailThumbnailLocation;
  private String detailImageLocation;
}
