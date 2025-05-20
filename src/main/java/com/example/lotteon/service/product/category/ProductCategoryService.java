package com.example.lotteon.service.product.category;

import com.example.lotteon.dto.product.ProductCategoryDTO;
import com.example.lotteon.dto.product.ProductSubCategoryDTO;
import com.example.lotteon.entity.product.ProductCategory;
import com.example.lotteon.entity.product.ProductSubCategory;
import com.example.lotteon.mapper.ProductCategoryMapper;
import com.example.lotteon.repository.jpa.product.category.ProductCategoryRepository;
import com.example.lotteon.service.admin.CacheService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

  private final CacheService cacheService;
  private final ProductCategoryRepository repo;
  private final ProductCategoryMapper categoryMapper;
  private final ModelMapper mapper;

  @PersistenceContext
  private final EntityManager em;

  public List<ProductCategoryDTO> getAll() {
    List<ProductCategoryDTO> cachedCategories = cacheService.getCachedCategory();
    if (cachedCategories == null) { //캐시된 카테고리 데이터가 없는 경우
      List<ProductCategoryDTO> categories = repo.findAll().stream()
          .map((category) -> { //MySQL에서 조회
            return mapper.map(category, ProductCategoryDTO.class);
          }).toList();
      cacheService.cacheCategory(categories); //캐싱
      cachedCategories = categories;
    }
    return cachedCategories;
  }

  public Map<ProductCategoryDTO, List<ProductSubCategoryDTO>> listWithSubCategories() {
    Map<ProductCategory, List<ProductSubCategory>> entityMap = repo.findAllWithSubCategories();// Entity Hash map 가져오기
    Map<ProductCategoryDTO, List<ProductSubCategoryDTO>> dtoMap = new LinkedHashMap<>();//DTO hash map 초기화
    for (Entry<ProductCategory, List<ProductSubCategory>> entry : entityMap.entrySet()) {// Entity hash map iteration
      ProductCategoryDTO dto = mapper.map(entry.getKey(), ProductCategoryDTO.class); //DTO 변환
      List<ProductSubCategory> subCategories = entry.getValue(); // subCategory entity 리스트 가져오기
      List<ProductSubCategoryDTO> subCategoryDTOs = new ArrayList<>();// ProductSubCategoryDTO 리스트 초기화
      for (ProductSubCategory subCategory : subCategories) { //ProductSubCategory entity list iteration
        ProductSubCategoryDTO subCategoryDTO = mapper.map(subCategory, ProductSubCategoryDTO.class);
        subCategoryDTOs.add(subCategoryDTO);
      }
      dtoMap.put(dto, subCategoryDTOs);
    }
    return dtoMap;
  }

  @Transactional
  public void update(List<ProductCategoryDTO> categories) {
    categoryMapper.updateToTempSequence(categories);
    categoryMapper.updateToFinalSequence(categories);
  }
  //  // 모든 항목을 임시 값으로 변경 (해시 또는 음수값으로)
  //  for (ProductCategoryDTO category : categories) {
  //    ProductCategory existing = repo.findById(category.getId()).orElseThrow();

  //    // 임시로 sequence를 고유하게 설정 (-id 활용)
  //    ProductCategory temp = new ProductCategory(
  //        existing.getId(),
  //        existing.getName(),
  //        -existing.getSequence()
  //    );
  //    repo.save(temp);
  //  }
  //  repo.flush(); // 임시 값 반영

  //  // 원하는 순서로 sequence 재설정
  //  for (ProductCategoryDTO category : categories) {
  //    ProductCategory updated = new ProductCategory(
  //        category.getId(),
  //        category.getName(),
  //        category.getSequence()
  //    );
  //    repo.save(updated);
  //  }
  //  repo.flush(); // 실제 값 반영
  //}
}
