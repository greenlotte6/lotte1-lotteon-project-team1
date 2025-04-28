package com.example.lotteon.repository.seller;

import com.example.lotteon.entity.seller.QSeller;
import com.example.lotteon.entity.seller.Seller;
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

  @Override
  public boolean existsByBusinessNumber(String businessNumber) {
    return query.selectFrom(seller)
        .where(seller.sellerCompositeKey.businessNumber.eq(businessNumber)).fetchOne() != null;
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
        .where(seller.sellerCompositeKey.user.contact.eq(contact))
        .fetch();
    return new PageImpl<>(sellers, pageable, sellers.size());
  }

  @Override
  public Page<Seller> findAllByBusinessNumber(String businessNumber, Pageable pageable) {
    List<Seller> sellers = query.selectFrom(seller)
        .where(seller.sellerCompositeKey.businessNumber.eq(businessNumber))
        .fetch();
    return new PageImpl<>(sellers, pageable, sellers.size());
  }

  @Override
  @Transactional
  public void deleteByBusinessNumbers(List<String> businessNumbers) {
    query.delete(seller)
        .where(seller.sellerCompositeKey.businessNumber.in(businessNumbers))
        .execute();
  }

  @Override
  @Transactional
  public void updateStatus(String businessNumber, String newStatus) {
    query.update(seller)
        .where(seller.sellerCompositeKey.businessNumber.eq(businessNumber))
        .set(seller.status, newStatus)
        .execute();
  }
}
