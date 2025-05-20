package com.example.lotteon.mapper;

import com.example.lotteon.dto.product.ProductCategoryDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductCategoryMapper {

  void updateToTempSequence(@Param("list") List<ProductCategoryDTO> list);

  void updateToFinalSequence(@Param("list") List<ProductCategoryDTO> list);
}
