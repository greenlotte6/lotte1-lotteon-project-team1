package com.example.lotteon.repository.product.category;

import com.example.lotteon.entity.product.ProductCategory;
import com.example.lotteon.entity.product.QProductCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ProductCategoryCustomRepositoryImpl implements ProductCategoryCustomRepository {

  private final JPAQueryFactory query;
  private final QProductCategory category = QProductCategory.productCategory;

  @Override
  @Transactional
  public void update(ProductCategory category) {
  }
}
