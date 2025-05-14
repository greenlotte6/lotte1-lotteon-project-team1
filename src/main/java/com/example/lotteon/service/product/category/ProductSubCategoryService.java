package com.example.lotteon.service.product.category;

import com.example.lotteon.dto.product.ProductSubCategoryDTO;
import com.example.lotteon.repository.product.category.ProductSubCategoryRepository;
import com.example.lotteon.service.admin.CacheService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSubCategoryService {

  private final CacheService cacheService;
  private final ModelMapper mapper;
  private final ProductSubCategoryRepository repo;

  public List<ProductSubCategoryDTO> getAll() {
    List<ProductSubCategoryDTO> cachedSubCategories = cacheService.getCachedSubCategory();

    if (cachedSubCategories == null) { // 캐시된 2차 카테고리 데이터가 없는 경우
      List<ProductSubCategoryDTO> subCategories = repo.findAll().stream()
          .map((subCategory) -> {
            return mapper.map(subCategory, ProductSubCategoryDTO.class);
          }).toList(); // MySQL에서 조회
      cacheService.cacheSubCategory(subCategories); // 캐싱
      cachedSubCategories = subCategories;
    }
    return cachedSubCategories;
  }
}
