package com.example.lotteon.repository.product;

import com.example.lotteon.entity.product.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByMemberId(String memberId);

    Optional<Cart> findByProductIdAndMemberId(int productId, String memberId);

    Cart findByMemberIdAndProduct_Id(String memberId, int productId);


}
