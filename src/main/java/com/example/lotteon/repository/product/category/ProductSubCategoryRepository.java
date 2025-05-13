package com.example.lotteon.repository.product.category;

import com.example.lotteon.entity.product.ProductSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSubCategoryRepository extends JpaRepository<ProductSubCategory, Integer>,
    ProductSubCategoryCustomRepository {

}
