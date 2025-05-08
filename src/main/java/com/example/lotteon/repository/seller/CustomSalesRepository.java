package com.example.lotteon.repository.seller;

import com.example.lotteon.entity.seller.Sales;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomSalesRepository {

  List<Sales> findAll();
}
