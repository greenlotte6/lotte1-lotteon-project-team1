package com.example.lotteon.service.product.category;

import com.example.lotteon.dto.product.ProductCategoryDTO;
import com.example.lotteon.dto.product.ProductSubCategoryDTO;
import com.example.lotteon.entity.product.ProductCategory;
import com.example.lotteon.entity.product.ProductSubCategory;
import com.example.lotteon.repository.product.category.ProductCategoryRepository;
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

  private final ProductCategoryRepository repo;
  private final ModelMapper mapper;

  public List<ProductCategory> getAll() {
    return repo.findAll();
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

  public void update(ProductCategory category) {
    repo.update(category);
  }
}
