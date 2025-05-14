package com.example.lotteon.service.product.category;

import com.example.lotteon.dto.product.CategoryFormDTO;
import com.example.lotteon.dto.product.ProductCategoryDTO;
import com.example.lotteon.dto.product.ProductSubCategoryDTO;
import com.example.lotteon.entity.product.ProductCategory;
import com.example.lotteon.entity.product.ProductSubCategory;
import com.example.lotteon.repository.product.category.ProductCategoryRepository;
import com.example.lotteon.service.admin.CacheService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

  private final CacheService cacheService;
  private final ProductCategoryRepository repo;
  private final ModelMapper mapper;

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

  //TODO:  Move logic below to Repository's update() method, which has @Transactional annotation.
  public void update(CategoryFormDTO form) {
    List<ProductCategoryDTO> categories = form.getCategories();
    for (ProductCategoryDTO category : categories) {
      //현재 category의 sequence를 가지고 있는 row 조회
      int originalSrcSeq = repo.findById(category.getId()).get().getSequence();
      int srcSequence = category.getSequence();
      ProductCategory dest = repo.findBySequence(srcSequence);

      //조회된 row의 sequence를 해당 row의 category name의 해시로 변경(임시적으로)
      ProductCategoryDTO destDTO = mapper.map(dest, ProductCategoryDTO.class);
      destDTO.setSequence(destDTO.getName().hashCode()); //임시 순번(hash of category name)으로 업데이트
      ProductCategory destWithTempSequence = mapper.map(destDTO, ProductCategory.class);
      repo.save(destWithTempSequence);

      //현재 category의 id와 일치하는 row 조회
      ProductCategory src = repo.findById(category.getId()).orElse(null);

      //조회된 row의 sequence를 클라이언트가 새롭게 설정한 순번으로 업데이트
      if (src != null) {
        ProductCategoryDTO srcDTO = mapper.map(src, ProductCategoryDTO.class);
        srcDTO.setSequence(category.getSequence());
        ProductCategory srcWithNewSequence = mapper.map(srcDTO, ProductCategory.class);
        repo.save(srcWithNewSequence);
      }

      // 임시 sequence를 가지고 있는 row의 sequence를 현재 category의 원래 sequence로 변경(swap)
      destDTO.setSequence(originalSrcSeq);
      ProductCategory destWithOriginalSrcSeq = mapper.map(destDTO, ProductCategory.class);
      repo.save(destWithOriginalSrcSeq);
    }
  }
}
