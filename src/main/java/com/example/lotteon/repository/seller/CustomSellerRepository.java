package com.example.lotteon.repository.seller;

import org.springframework.stereotype.Repository;

@Repository
public interface CustomSellerRepository {

  boolean existsByBusinessNumber(String businessNumber);
}
