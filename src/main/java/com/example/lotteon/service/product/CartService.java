package com.example.lotteon.service.product;

import com.example.lotteon.entity.product.Cart;
import com.example.lotteon.entity.product.CartId;
import com.example.lotteon.repository.product.CartRepository;
import com.example.lotteon.repository.product.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingCartService {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;

  @Transactional
  public void addCart(int productId, String memberId, int quantity) {
  }

  @Transactional(readOnly = true)
  public List<Cart> getCartItems(String memberId) {
    return cartRepository.findByMemberId(memberId);
  }

  @Transactional
  public void deleteByCompositeIds(List<CartId> cartIds) {
    cartRepository.deleteAllByIdInBatch(cartIds);
  }
}

