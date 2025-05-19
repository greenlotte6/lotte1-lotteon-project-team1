package com.example.lotteon.repository.jpa.seller;

import com.example.lotteon.entity.seller.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer>, CustomSalesRepository {

}
