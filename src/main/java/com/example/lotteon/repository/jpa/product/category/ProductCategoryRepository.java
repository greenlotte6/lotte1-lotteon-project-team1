package com.example.lotteon.repository.jpa.product.category;

import com.example.lotteon.entity.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>,
    ProductCategoryCustomRepository {

}
