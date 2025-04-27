package com.example.lotteon.repository.seller;

import com.example.lotteon.entity.seller.QSeller;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomSellerRepositoryImpl implements CustomSellerRepository {

  private final JPAQueryFactory queryFactory;
  private final QSeller seller = QSeller.seller;

  @Override
  public boolean existsByBusinessNumber(String businessNumber) {
    return queryFactory.selectFrom(seller)
        .where(seller.sellerCompositeKey.businessNumber.eq(businessNumber)).fetchOne() != null;
  }
}
