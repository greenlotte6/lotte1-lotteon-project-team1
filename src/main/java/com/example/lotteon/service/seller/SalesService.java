package com.example.lotteon.service.seller;

import com.example.lotteon.entity.seller.Sales;
import com.example.lotteon.repository.seller.SalesRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesService {

  private final SalesRepository repo;

  public List<Sales> findAll() {
    return repo.findAll();
  }
}
