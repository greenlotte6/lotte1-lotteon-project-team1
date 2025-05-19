package com.example.lotteon.service.product;

import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.repository.jpa.product.ProductDetailRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductDetailService {

  private final ProductDetailRepository detailRepository;
  private final ModelMapper modelMapper;

  // 상세 페이지 데이터 출력
  public ProductDTO productDetail(int id) {
    log.info("id: " + id);

    // findbyId는 optional타입으로 옴
    Optional<Product> optProduct = detailRepository.findById(id);

    if (optProduct.isPresent()) {

      Product product = optProduct.get();

      ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
      log.info("productDTO: " + productDTO);
      return productDTO;
    }
    return null;
  }

}
