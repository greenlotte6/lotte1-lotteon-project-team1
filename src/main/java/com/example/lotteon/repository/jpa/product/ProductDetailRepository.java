package com.example.lotteon.repository.jpa.product;

import com.example.lotteon.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<Product, Integer> {

}
