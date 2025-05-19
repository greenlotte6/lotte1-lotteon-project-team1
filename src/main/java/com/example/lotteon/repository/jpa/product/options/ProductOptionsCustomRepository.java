package com.example.lotteon.repository.jpa.product.options;

import com.example.lotteon.entity.product.ProductOptions;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionsCustomRepository {

  void update(ProductOptions options);

  int findLatestId();

}
