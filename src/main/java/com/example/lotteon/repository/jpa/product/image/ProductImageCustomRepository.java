package com.example.lotteon.repository.jpa.product.image;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageCustomRepository {

  void updateListThumbnail(int imageId, String location);

  void updateMainThumbnail(int imageId, String location);

  void updateDetailImage(int imageId, String location);

  void updateDetailThumbnail(int imageId, String location);
}
