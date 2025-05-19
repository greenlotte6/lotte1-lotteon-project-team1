package com.example.lotteon.repository.jpa.product.category;

import com.example.lotteon.entity.product.ProductCategory;
import com.example.lotteon.entity.product.ProductSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSubCategoryRepository extends JpaRepository<ProductSubCategory, String>,
        ProductSubCategoryCustomRepository {

    List<ProductSubCategory> findByCategoryOrderBySequence(ProductCategory category);
}