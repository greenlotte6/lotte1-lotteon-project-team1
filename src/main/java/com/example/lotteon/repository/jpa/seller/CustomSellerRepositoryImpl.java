package com.example.lotteon.repository.jpa.seller;

import com.example.lotteon.entity.seller.QSeller;
import com.example.lotteon.entity.seller.Seller;
import com.example.lotteon.entity.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CustomSellerRepositoryImpl implements CustomSellerRepository {

  private final JPAQueryFactory query;
  private final QSeller seller = QSeller.seller;
  private final QUser user = QUser.user;

  @Override
  public Seller findByBusinessNumber(String businessNumber) {
    return query.selectFrom(seller)
        .where(seller.sellerId.businessNumber.eq(businessNumber))
        .fetchOne();
  }

  @Override
  public boolean existsByBusinessNumber(String businessNumber) {
    return query.selectFrom(seller)
        .where(seller.sellerId.businessNumber.eq(businessNumber)).fetchOne() != null;
  }

  @Override
  public Page<Seller> findAllByCeo(String ceo, Pageable pageable) {
    List<Seller> sellers = query.selectFrom(seller)
        .where(seller.ceo.eq(ceo))
        .fetch();

    return new PageImpl<>(sellers, pageable, sellers.size());
  }

  @Override
  public Page<Seller> findAllByCompanyName(String companyName, Pageable pageable) {
    List<Seller> sellers = query.selectFrom(seller)
        .where(seller.companyName.eq(companyName))
        .fetch();
    return new PageImpl<>(sellers, pageable, sellers.size());
  }

  @Override
  public Page<Seller> findAllByContact(String contact, Pageable pageable) {
    List<Seller> sellers = query.selectFrom(seller)
        .where(seller.sellerId.user.contact.eq(contact))
        .fetch();
    return new PageImpl<>(sellers, pageable, sellers.size());
  }

  @Override
  public Page<Seller> findAllByBusinessNumber(String businessNumber, Pageable pageable) {
    List<Seller> sellers = query.selectFrom(seller)
        .where(seller.sellerId.businessNumber.eq(businessNumber))
        .fetch();
    return new PageImpl<>(sellers, pageable, sellers.size());
  }

  @Override
  @Transactional
  public void deleteByBusinessNumbers(List<String> businessNumbers) {
    // 클라이언트에서 선택된 판매자의 사업자등록번호로 user id를 조회
    List<String> uids = query.select(seller.sellerId.user.id).from(seller)
        .where(seller.sellerId.businessNumber.in(businessNumbers))
        .fetch();
    // user 테이블에서 id가 uids에 있는 row 삭제
    query.delete(user)
        .where(user.id.in(uids))
        .execute();
  }

  @Override
  @Transactional
  public void updateStatus(String businessNumber, String newStatus) {
    query.update(seller)
        .where(seller.sellerId.businessNumber.eq(businessNumber))
        .set(seller.status, newStatus)
        .execute();
  }
}
