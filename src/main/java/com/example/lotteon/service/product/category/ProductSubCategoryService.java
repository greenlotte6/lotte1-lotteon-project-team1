package com.example.lotteon.service.product.category;

import com.example.lotteon.entity.product.ProductSubCategory;
import com.example.lotteon.repository.product.category.ProductSubCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSubCategoryService {

  private final ProductSubCategoryRepository repo;

  public List<ProductSubCategory> getAll() {
    return repo.findAll();
  }
}
