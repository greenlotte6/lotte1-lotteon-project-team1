package com.example.lotteon.repository.jpa.product.image;

import com.example.lotteon.entity.product.QProductImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ProductImageCustomRepositoryImpl implements ProductImageCustomRepository {

  private final JPAQueryFactory query;
  private final QProductImage image = QProductImage.productImage;

  @Override
  @Transactional
  public void updateListThumbnail(int imageId, String location) {
    query.update(this.image)
        .set(this.image.listThumbnailLocation, location)
        .where(this.image.id.eq(imageId))
        .execute();
  }

  @Override
  @Transactional
  public void updateMainThumbnail(int imageId, String location) {
    query.update(this.image)
        .set(this.image.mainThumbnailLocation, location)
        .where(this.image.id.eq(imageId))
        .execute();
  }

  @Override
  @Transactional
  public void updateDetailImage(int imageId, String location) {
    query.update(this.image)
        .set(this.image.detailImageLocation, location)
        .where(this.image.id.eq(imageId))
        .execute();
  }

  @Override
  @Transactional
  public void updateDetailThumbnail(int imageId, String location) {
    query.update(this.image)
        .set(this.image.detailThumbnailLocation, location)
        .where(this.image.id.eq(imageId))
        .execute();
  }
}
