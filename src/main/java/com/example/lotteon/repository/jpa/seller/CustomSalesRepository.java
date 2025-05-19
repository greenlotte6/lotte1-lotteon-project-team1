package com.example.lotteon.repository.jpa.seller;

import com.querydsl.core.Tuple;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomSalesRepository {

  List<Tuple> findAllAsTuples(String sort);

  List<Tuple> findAllAsTuples(String sort, String sellerId);

  List<Tuple> findAllAsTuplesWithSortDaily(String sort);

  List<Tuple> findAllAsTuplesWithSortDaily(String sort, String sellerId);

  List<Tuple> findAllAsTuplesWithSortWeekly(String sort);

  List<Tuple> findAllAsTuplesWithSortWeekly(String sort, String sellerId);

  List<Tuple> findAllAsTuplesWithSortMonthly(String sort);

  List<Tuple> findAllAsTuplesWithSortMonthly(String sort, String sellerId);
}
