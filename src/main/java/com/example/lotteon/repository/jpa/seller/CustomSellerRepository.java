package com.example.lotteon.repository.jpa.seller;

import com.example.lotteon.entity.seller.Seller;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomSellerRepository {

  boolean existsByBusinessNumber(String businessNumber);

  Seller findByBusinessNumber(String businessNumber);

  Page<Seller> findAllByCeo(String ceo, Pageable pageable);

  Page<Seller> findAllByCompanyName(String companyName, Pageable pageable);

  Page<Seller> findAllByContact(String contact, Pageable pageable);

  Page<Seller> findAllByBusinessNumber(String businessNumber, Pageable pageable);

  void deleteByBusinessNumbers(List<String> businessNumbers);

  void updateStatus(String businessNumber, String newStatus);
}
