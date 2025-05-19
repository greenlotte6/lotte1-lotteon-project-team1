package com.example.lotteon.repository.jpa.coupon.impl;


import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.entity.coupon.QCoupon;
import com.example.lotteon.entity.coupon.QCoupon_History;
import com.example.lotteon.repository.jpa.coupon.custom.CouponHistoryRepositoryCustom;
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
public class CouponHistoryRepositoryImpl implements CouponHistoryRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;
  private final QCoupon_History qCoupon_History = QCoupon_History.coupon_History;

  @Override
  public Page<Tuple> selectAllForList(Pageable pageable, int id) {
    QCoupon qCoupon = QCoupon.coupon;

    BooleanExpression expression = null;

    long total = jpaQueryFactory
        .select(qCoupon_History.count())
        .from(qCoupon_History)
        .where(expression)
        .fetchOne();

    List<Tuple> tupleList = jpaQueryFactory
        .select(qCoupon_History, qCoupon_History.status)  // status 추가
        .from(qCoupon_History)
        .where(expression)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(qCoupon_History.id.desc())
        .fetch();

    return new PageImpl<>(tupleList, pageable, total);
  }

  @Override
  public Page<Tuple> selectAllForSearch(PageRequestDTO pageRequestDTO, Pageable pageable, int id,
      String name, int coupon_id, String user_id) {

    String searchType = pageRequestDTO.getSearchType();
    String keyword = pageRequestDTO.getKeyword();

    BooleanExpression expression = qCoupon_History.id.isNotNull();

    if (name != null && !name.trim().isEmpty()) {
      expression = expression.and(qCoupon_History.coupon.name.containsIgnoreCase(name));
    }
    if (coupon_id != 0) {
      expression = expression.and(qCoupon_History.coupon.id.eq(coupon_id));
    }
    if (user_id != null && !user_id.trim().isEmpty()) {
      expression = expression.and(qCoupon_History.member.memberId.user.id.eq(user_id));
    }

    if (keyword != null && !keyword.trim().isEmpty()) {
      switch (searchType) {
        case "id":
          try {
            int keywordId = Integer.parseInt(keyword);
            expression = expression.and(qCoupon_History.id.eq(keywordId));
          } catch (NumberFormatException e) {
            expression = expression.and(qCoupon_History.id.eq(-1));
          }
          break;
        case "coupon_id":
          try {
            int keywordCouponId = Integer.parseInt(keyword);
            expression = expression.and(qCoupon_History.coupon.id.eq(keywordCouponId));
          } catch (NumberFormatException e) {
            expression = expression.and(qCoupon_History.coupon.id.eq(-1));
          }
          break;
        case "user_id":
          // user_id 가 String이면 그대로 eq 사용
          expression = expression.and(qCoupon_History.member.memberId.user.id.eq(keyword));
          break;
        case "name":
          expression = expression.and(qCoupon_History.coupon.name.containsIgnoreCase(keyword));
          break;
      }
    }

    long total = jpaQueryFactory
        .select(qCoupon_History.count())
        .from(qCoupon_History)
        .where(expression)
        .fetchOne();

    List<Tuple> tupleList = jpaQueryFactory
        .select(qCoupon_History, qCoupon_History.status)
        .from(qCoupon_History)
        .where(expression)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(qCoupon_History.id.desc())
        .fetch();

    return new PageImpl<>(tupleList, pageable, total);
  }

}
