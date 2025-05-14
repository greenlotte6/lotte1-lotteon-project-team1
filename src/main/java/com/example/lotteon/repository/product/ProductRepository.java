package com.example.lotteon.repository.product;

import com.example.lotteon.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>,
    ProductCustomRepository {

    default Product getByIdOrThrow(Integer id) {
        return findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 상품이 존재하지 않습니다. id = " + id));
    }

    @Query("SELECT oi.product AS product, SUM(oi.amount) AS totalAmount " +
            "FROM OrderItem oi " +
            "GROUP BY oi.product " +
            "ORDER BY totalAmount DESC")
    List<Object[]> findProductsOrderBySalesDesc();
}
