package com.example.lotteon.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class ProductImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "list_thumbnail_loc")
  private String listThumbnailLocation;

  @Column(name = "main_thumbnail_loc")
  private String mainThumbnailLocation;

  @Column(name = "detail_thumbnail_loc")
  private String detailThumbnailLocation;

  @Column(name = "detail_image_loc")
  private String detailImageLocation;
}
