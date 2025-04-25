package com.example.lotteon.service.seller;

import com.example.lotteon.entity.seller.Seller;
import com.example.lotteon.repository.seller.SellerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService {

  private final SellerRepository repo;

  public List<Seller> getAll() {
    return repo.findAll();
  }
}
