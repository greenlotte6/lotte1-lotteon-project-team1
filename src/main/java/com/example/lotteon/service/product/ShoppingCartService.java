package com.example.lotteon.service.product;

import com.example.lotteon.entity.product.Cart;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.repository.product.CartRepository;
import com.example.lotteon.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void addCart(int productId, String memberId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        Cart existingCart = cartRepository.findByMemberIdAndProduct_Id(memberId, productId);

        if (existingCart != null) {
            // 이미 장바구니에 존재하면 수량만 업데이트
            int newQuantity = existingCart.getQuantity() + quantity;
            existingCart.updateQuantity(newQuantity, product.getPrice());
            cartRepository.save(existingCart);
        } else {
            Cart newCart = Cart.builder()
                    .memberId(memberId)
                    .product(product)
                    .quantity(quantity)
                    .price(product.getPrice())
                    .totalPrice(product.getPrice() * quantity)
                    .dil(3000) // 배송비는 최초 1회만 부가
                    .build();
            cartRepository.save(newCart);
        }
    }

    @Transactional(readOnly = true)
    public List<Cart> getCartItems(String memberId) {
        return cartRepository.findByMemberId(memberId);
    }

    public void deleteByIds(List<Long> ids) {
        cartRepository.deleteAllByIdInBatch(ids);
    }
}

