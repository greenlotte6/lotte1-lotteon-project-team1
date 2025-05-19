package com.example.lotteon.repository.jpa.coupon.impl;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.entity.coupon.QCoupon;
import com.example.lotteon.entity.cs.QArticle_Type;
import com.example.lotteon.repository.jpa.coupon.custom.CouponRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CouponRepositoryImpl implements CouponRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private final QCoupon qCoupon = QCoupon.coupon;

  @Override
  public Page<Tuple> selectAllForList(Pageable pageable, int id, String name, String seller_id) {
    QArticle_Type qType = QArticle_Type.article_Type;

    BooleanExpression expression = null;

    long total = queryFactory
        .select(qCoupon.count())
        .from(qCoupon)
        .where(expression)
        .fetchOne();

    List<Tuple> tupleList = queryFactory
        .select(qCoupon, qCoupon.status)  // status 추가
        .from(qCoupon)
        .where(expression)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(qCoupon.id.desc())
        .fetch();

    return new PageImpl<>(tupleList, pageable, total);
  }


  @Override
  public Page<Tuple> selectAllForSearch(PageRequestDTO pageRequestDTO, Pageable pageable, int id,
      String name, String seller_id) {

    String searchType = pageRequestDTO.getSearchType();
    String keyword = pageRequestDTO.getKeyword();

    BooleanExpression expression = qCoupon.id.isNotNull(); // 기본 조건

    if (keyword != null && !keyword.trim().isEmpty()) {
      switch (searchType) {
        case "id":
          try {
            int keywordId = Integer.parseInt(keyword);
            expression = expression.and(qCoupon.id.eq(keywordId));
          } catch (NumberFormatException e) {
            expression = expression.and(qCoupon.id.eq(-1)); // 존재할 수 없는 값
          }
          break;
        case "name":
          expression = expression.and(qCoupon.name.containsIgnoreCase(keyword));
          break;
        case "seller_id":
          expression = expression.and(qCoupon.seller_id.containsIgnoreCase(keyword));
          break;
      }
    }

    long total = queryFactory
        .select(qCoupon.count())
        .from(qCoupon)
        .where(expression)
        .fetchOne();

    List<Tuple> tupleList = queryFactory
        .select(qCoupon, qCoupon.status)
        .from(qCoupon)
        .where(expression)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(qCoupon.id.desc())
        .fetch();

    return new PageImpl<>(tupleList, pageable, total);
  }


}
