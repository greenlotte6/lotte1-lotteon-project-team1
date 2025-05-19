package com.example.lotteon.repository.jpa.product.category;

import com.example.lotteon.entity.product.ProductCategory;
import com.example.lotteon.entity.product.ProductSubCategory;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryCustomRepository {

  ProductCategory findBySequence(int sequence);

  Map<ProductCategory, List<ProductSubCategory>> findAllWithSubCategories();
}
