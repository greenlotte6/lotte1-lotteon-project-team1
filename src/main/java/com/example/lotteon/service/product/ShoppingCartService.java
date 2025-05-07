package com.example.lotteon.service.product;

import com.example.lotteon.entity.product.Cart;
import com.example.lotteon.entity.product.CartId;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.repository.product.CartRepository;
import com.example.lotteon.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

        // 복합키 기준으로 기존 Cart 찾기
        Optional<Cart> optionalCart = cartRepository.findByMemberIdAndProductId(memberId, productId);

        if (optionalCart.isPresent()) {
            Cart existingCart = optionalCart.get();
            int newQuantity = existingCart.getQuantity() + quantity;
            existingCart.updateQuantity(newQuantity, product.getPrice());
            cartRepository.save(existingCart);
        } else {
            Cart newCart = Cart.builder()
                    .memberId(memberId)
                    .productId(product.getId())
                    .product(product)
                    .quantity(quantity)
                    .price(product.getPrice())
                    .totalPrice(product.getPrice() * quantity)
                    .dil(product.getDeliveryFee())
                    .build();
            cartRepository.save(newCart);
        }
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

