package com.example.lotteon.repository.product;

import com.example.lotteon.entity.product.Cart;
import com.example.lotteon.entity.product.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId> {
    List<Cart> findByMemberId(String memberId);
}