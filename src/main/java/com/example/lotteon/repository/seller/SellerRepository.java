package com.example.lotteon.repository.seller;

import com.example.lotteon.entity.seller.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer>, CustomSellerRepository {

  Page<Seller> findAll(Pageable pageable);
}
