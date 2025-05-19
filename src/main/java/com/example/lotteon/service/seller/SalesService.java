package com.example.lotteon.service.seller;

import com.example.lotteon.dto.seller.SalesWrapper;
import com.example.lotteon.repository.jpa.seller.SalesRepository;
import com.querydsl.core.Tuple;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesService {

  private final SalesRepository repo;
  private final ModelMapper mapper;

  private List<SalesWrapper> toList(List<Tuple> tuples) {
    List<SalesWrapper> wrappers = new ArrayList<>();

    for (Tuple tuple : tuples) {
      SalesWrapper wrapper = SalesWrapper.builder()
          .tuple(tuple)
          .build(mapper);
      wrappers.add(wrapper);
    }
    return wrappers;
  }

  public Page<SalesWrapper> list(String filter, String sort, Pageable pageable) {
    List<Tuple> tuples = new ArrayList<>();
    switch (filter) {
      case "all": {
        tuples = repo.findAllAsTuples(sort);
        break;
      }
      case "daily": {
        tuples = repo.findAllAsTuplesWithSortDaily(sort);
        break;
      }
      case "weekly": {
        tuples = repo.findAllAsTuplesWithSortWeekly(sort);
        break;
      }
      case "monthly": {
        tuples = repo.findAllAsTuplesWithSortMonthly(sort);
        break;
      }
    }
    List<SalesWrapper> wrappers = toList(tuples);
    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  public Page<SalesWrapper> list(String filter, String sort, String sellerId,
      Pageable pageable) {
    List<Tuple> tuples = new ArrayList<>();
    switch (filter) {
      case "all": {
        tuples = repo.findAllAsTuples(sort, sellerId);
        break;
      }
      case "daily": {
        tuples = repo.findAllAsTuplesWithSortDaily(sort, sellerId);
        break;
      }
      case "weekly": {
        tuples = repo.findAllAsTuplesWithSortWeekly(sort, sellerId);
        break;
      }
      case "monthly": {
        tuples = repo.findAllAsTuplesWithSortMonthly(sort, sellerId);
        break;
      }
    }
    List<SalesWrapper> wrappers = toList(tuples);
    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }
}
