package com.example.lotteon.repository.jpa.product.options;

import com.example.lotteon.entity.product.ProductOptions;
import com.example.lotteon.entity.product.QProductOptions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ProductOptionsCustomRepositoryImpl implements ProductOptionsCustomRepository {

  private final JPAQueryFactory query;
  private final QProductOptions options = QProductOptions.productOptions;

  @Override
  @Transactional
  public void update(ProductOptions options) {
    query.update(this.options)
        .set(this.options.option, options.getOption())
        .set(this.options.value, options.getValue())
        .where(this.options.id.eq(options.getId()))
        .execute();
  }

  @Override
  public int findLatestId() {
    Integer id = query.select(options.id)
        .from(options)
        .orderBy(options.id.desc())
        .limit(1)
        .fetchOne();
    if (id == null) {
      return 0;
    }
    return id;
  }
}
