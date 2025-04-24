package com.example.lotteon.repository.product;

import com.example.lotteon.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository  extends JpaRepository<Product, Integer> {
}
