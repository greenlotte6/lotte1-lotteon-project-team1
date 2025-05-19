package com.example.lotteon.repository.jpa.product;

import com.example.lotteon.entity.product.Product;
import com.example.lotteon.entity.product.ProductOptions;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCustomRepository {

  int getLatestIdAndIncrement();

  Page<Product> findAll(Pageable pageable);

  Page<Product> findById(int id, Pageable pageable);

  Page<Product> findByName(String name, Pageable pageable);

  Page<Product> findByCompany(String company, Pageable pageable);

  List<ProductOptions> findOptionsByProductId(int productId);

  ProductOptions findOptionByProductId(int productId);

  void updateStatusById(int id, String status);

  void updateById(int id, Product product);

  void deleteById(int id);


}
