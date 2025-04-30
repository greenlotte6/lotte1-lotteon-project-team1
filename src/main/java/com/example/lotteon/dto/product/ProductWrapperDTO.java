package com.example.lotteon.dto.product;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 상품 등록/수정 요청 시 사용되는 DTO
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductWrapperDTO {

  private ProductDTO product;
  private List<ProductOptionsDTO> options;
}
