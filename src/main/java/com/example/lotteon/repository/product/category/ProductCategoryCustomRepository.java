package com.example.lotteon.repository.product.category;

import com.example.lotteon.entity.product.ProductCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryCustomRepository {

  void update(ProductCategory category);
}
