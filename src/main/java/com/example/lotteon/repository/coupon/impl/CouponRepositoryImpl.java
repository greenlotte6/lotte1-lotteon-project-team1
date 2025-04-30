package com.example.lotteon.repository.coupon.impl;

import com.example.lotteon.entity.coupon.QCoupon;
import com.example.lotteon.entity.cs.QArticle_Type;
import com.example.lotteon.repository.coupon.custom.CouponRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
