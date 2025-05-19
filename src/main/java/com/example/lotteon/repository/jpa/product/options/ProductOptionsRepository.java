package com.example.lotteon.repository.jpa.product.options;

import com.example.lotteon.entity.product.ProductOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductOptionsRepository extends JpaRepository<ProductOptions, Integer>,
    ProductOptionsCustomRepository {

  @Modifying
  @Transactional
  @Query(value =
      "INSERT INTO product_options (id, product_id, name, value) VALUES (:#{#options.id},:productId, :#{#options.option}, :#{#options.value})"
          + "ON DUPLICATE KEY UPDATE name = VALUES(name), value = VALUES(value)", nativeQuery = true)
  void upsert(@Param("productId") int productId, @Param("options") ProductOptions options);
}
