package com.example.lotteon.repository.jpa.cart;

import com.example.lotteon.entity.product.Cart;
import com.example.lotteon.entity.product.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId>, CustomCartRepository {

}

