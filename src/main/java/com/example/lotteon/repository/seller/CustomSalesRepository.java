package com.example.lotteon.repository.seller;

import com.querydsl.core.Tuple;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomSalesRepository {

  List<Tuple> findAllAsTuples();

  List<Tuple> findAllAsTuples(String sellerId);
}
