package com.example.lotteon.repository.jpa.product.image;

import com.example.lotteon.entity.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer>,
    ProductImageCustomRepository {

}
