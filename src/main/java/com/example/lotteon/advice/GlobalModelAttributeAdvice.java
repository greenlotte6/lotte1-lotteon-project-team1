package com.example.lotteon.advice;

import com.example.lotteon.entity.product.ProductCategory;
import com.example.lotteon.entity.product.ProductSubCategory;
import com.example.lotteon.repository.jpa.product.category.ProductCategoryRepository;
import com.example.lotteon.repository.jpa.product.category.ProductSubCategoryRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributeAdvice {

  private final ProductCategoryRepository productCategoryRepository;
  private final ProductSubCategoryRepository productSubCategoryRepository;

  @ModelAttribute("categoryMap")
  public Map<ProductCategory, List<ProductSubCategory>> populateCategoryMap() {
    List<ProductCategory> categories = productCategoryRepository.findAllByOrderBySequenceAsc();
    Map<ProductCategory, List<ProductSubCategory>> categoryMap = new LinkedHashMap<>();

    for (ProductCategory category : categories) {
      List<ProductSubCategory> subcategories =
          productSubCategoryRepository.findByCategoryOrderBySequence(category);
      categoryMap.put(category, subcategories);
    }

    return categoryMap;
  }
}