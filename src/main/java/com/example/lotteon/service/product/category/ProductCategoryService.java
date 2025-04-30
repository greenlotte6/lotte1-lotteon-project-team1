package com.example.lotteon.service.product.category;

import com.example.lotteon.entity.product.ProductCategory;
import com.example.lotteon.repository.product.category.ProductCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

  private final ProductCategoryRepository repo;

  public List<ProductCategory> getAll() {
    return repo.findAll();
  }

  public void update(ProductCategory category) {
    repo.update(category);
  }
}
