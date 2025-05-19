package com.example.lotteon.repository.jpa.cart;

import com.example.lotteon.entity.product.Cart;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomCartRepository {

  List<Cart> findAllByMemberId(String memberId);

  void deleteByMemberId(String memberId);
}
