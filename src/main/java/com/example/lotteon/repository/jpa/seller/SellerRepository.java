package com.example.lotteon.repository.jpa.seller;

import com.example.lotteon.entity.seller.Seller;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer>, CustomSellerRepository {

  Optional<Seller> findByCeoAndSellerIdUserEmail(String ceo, String email);

  Page<Seller> findAll(Pageable pageable);

  //마이페이지 셀러 디테일 코드
  Optional<Seller> findBySellerId_BusinessNumber(String businessNumber);
}
