package com.example.lotteon.service.product;

import com.example.lotteon.dto.product.CartDTO;
import com.example.lotteon.entity.product.Cart;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.repository.product.CartRepository;
import com.example.lotteon.repository.product.ProductRepository;
import com.example.lotteon.service.user.LoginUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShoppingCartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final LoginUserService loginUserService;
    private final ModelMapper modelMapper;

//    public void addCart(int productId) {
//
//        String loginUserId = loginUserService.getLoginUser();
//        log.info("LoginUserId: {}", loginUserId);
//
//        Optional<Product> optProduct = productRepository.findById(productId);
//        if (optProduct.isEmpty()) {
//            throw new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + productId);
//        }
//
//        Product product = optProduct.get();
//
//        Cart cart = Cart.builder()
//                .memberId(loginUserId)
//                .product(product)
//                .quantity(1)
//                .price(product.getPrice())
//                .totalPrice(product.getPrice())
//                .build();
//
//        cartRepository.save(cart);
//    }
//
//    public List<CartDTO> getCartList(String memberId) {
//        List<Cart> cartList = cartRepository.findByMemberId((memberId));
//        List<CartDTO> cartDTOList = new ArrayList<>();
//
//        for (Cart cart : cartList) {
//            CartDTO cartDTO = CartDTO.builder()
//                    .member_id(cart.getMemberId())
//                    .product_id(cart.getProduct().getId())
//                    .build();
//            cartDTOList.add(cartDTO);
//        }
//
//        return cartDTOList;
//    }

    public void addCart(int productId) {
        // 상품 ID로 상품을 찾음
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        // 장바구니 엔티티 생성
        Cart cart = Cart.builder()
                .product(product) // 상품 정보
                .quantity(1) // 기본 수량 1로 설정
                .price(product.getPrice()) // 상품 가격
                .totalPrice(product.getPrice()) // 총가격 = 가격 * 수량
                .build();

        // 장바구니에 추가
        cartRepository.save(cart);
    }

}