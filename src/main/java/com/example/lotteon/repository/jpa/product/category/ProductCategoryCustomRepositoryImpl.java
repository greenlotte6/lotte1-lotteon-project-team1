package com.example.lotteon.repository.jpa.product.category;

import com.example.lotteon.entity.product.ProductCategory;
import com.example.lotteon.entity.product.ProductSubCategory;
import com.example.lotteon.entity.product.QProductCategory;
import com.example.lotteon.entity.product.QProductSubCategory;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductCategoryCustomRepositoryImpl implements ProductCategoryCustomRepository {

  private final JPAQueryFactory query;
  private final QProductCategory category = QProductCategory.productCategory;
  private final QProductSubCategory subCategory = QProductSubCategory.productSubCategory;

  @Override
  public ProductCategory findBySequence(int sequence) {
    return query.selectFrom(category)
        .where(category.sequence.eq(sequence))
        .fetchOne();
  }

  @Override
  public Map<ProductCategory, List<ProductSubCategory>> findAllWithSubCategories() {
    List<Tuple> tuples = query
        .select(category, subCategory)
        .from(category)
        .leftJoin(subCategory).on(subCategory.category.id.eq(category.id))
        .orderBy(category.sequence.asc(), subCategory.sequence.asc())
        .fetch();

    return tuples.stream()
        .collect(Collectors.groupingBy(
            tuple -> tuple.get(category),
            LinkedHashMap::new, // 순서를 유지하도록 지정
            Collectors.mapping(
                tuple -> tuple.get(subCategory),
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> list.stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
                )
            )
        ));
  }
}
