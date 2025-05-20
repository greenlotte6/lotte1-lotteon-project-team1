package com.example.lotteon.repository.jpa.product;

import com.example.lotteon.entity.product.Product;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

  @Query("""
      SELECT oi.product 
      FROM OrderItem oi 
      GROUP BY oi.product 
      ORDER BY SUM(oi.amount) DESC
      """)
  List<Product> findTop5ByOrderBySalesDesc(Pageable pageable);

  @Query("""
    SELECT oi.product
    FROM OrderItem oi
    WHERE oi.product.subCategory.id = :subcategoryId
    GROUP BY oi.product
    ORDER BY SUM(oi.amount) DESC
""")
  List<Product> findBySubCategoryIdOrderBySalesDesc(String subcategoryId);

  @Query("SELECT p FROM Product p WHERE p.subCategory.id = :subcategoryId")
  List<Product> findBySubCategoryId(String subcategoryId, Sort sort);

  List<Product> findBySubCategory_Id(String subCategoryId);
}
