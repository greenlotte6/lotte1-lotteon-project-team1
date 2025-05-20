package com.example.lotteon.service.product.category;

import com.example.lotteon.dto.product.ProductSubCategoryDTO;
import com.example.lotteon.entity.product.ProductCategory;
import com.example.lotteon.entity.product.ProductSubCategory;
import com.example.lotteon.repository.jpa.product.category.ProductSubCategoryRepository;
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

  public void update(List<ProductSubCategoryDTO> subCategories) {
    // 모든 항목을 임시 값으로 변경 (해시 또는 음수값으로)
    for (ProductSubCategoryDTO category : subCategories) {
      ProductSubCategory existing = repo.findById(String.valueOf(category.getId())).orElseThrow();

      // 임시로 sequence를 고유하게 설정 (-id 활용)
      ProductSubCategory temp = new ProductSubCategory(
          existing.getId(),
          existing.getCategory(),
          existing.getName(),
          -existing.getSequence()
      );
      repo.save(temp);
    }
    repo.flush(); // 임시 값 반영

    // 원하는 순서로 sequence 재설정
    for (ProductSubCategoryDTO subCategory : subCategories) {
      ProductCategory category = mapper.map(subCategory.getCategory(), ProductCategory.class);
      ProductSubCategory updated = new ProductSubCategory(
          String.valueOf(subCategory.getId()),
          category,
          subCategory.getName(),
          subCategory.getSequence()
      );
      repo.save(updated);
    }
    repo.flush(); // 실제 값 반영
  }
}
