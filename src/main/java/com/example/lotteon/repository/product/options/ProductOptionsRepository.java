package com.example.lotteon.repository.product.options;

import com.example.lotteon.entity.product.ProductOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionsRepository extends JpaRepository<ProductOptions, Integer>,
    ProductOptionsCustomRepository {

}
