package com.example.lotteon.service.product.options;

import com.example.lotteon.dto.product.ProductOptionsDTO;
import com.example.lotteon.entity.product.ProductOptions;
import com.example.lotteon.repository.jpa.product.options.ProductOptionsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductOptionsService {

  private final ProductOptionsRepository repo;
  private final ModelMapper mapper;

  public ProductOptionsDTO getById(int id) {
    ProductOptions entity = repo.findById(id).orElse(null);
    if (entity != null) {
      return mapper.map(entity, ProductOptionsDTO.class);
    }
    return null;
  }

  public void update(List<ProductOptionsDTO> options) {
    for (ProductOptionsDTO option : options) {
      ProductOptions entity = mapper.map(option, ProductOptions.class);
      repo.update(entity);
    }
  }

  public int getLatestId() {
    return repo.findLatestId();
  }

  public void save(int productId, List<ProductOptionsDTO> options) {
    for (ProductOptionsDTO option : options) {
      repo.upsert(productId, mapper.map(option, ProductOptions.class));
    }
  }
}
