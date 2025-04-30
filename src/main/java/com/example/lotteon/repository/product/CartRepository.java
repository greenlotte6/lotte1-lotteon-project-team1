package com.example.lotteon.repository.product;

import com.example.lotteon.entity.product.Cart;
import com.example.lotteon.entity.product.CartId;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId> {

    List<Cart> findByMemberId(String memberId);

    @Query("SELECT c FROM Cart c WHERE c.memberId = :memberId AND c.product.id = :productId")
    Optional<Cart> findByMemberIdAndProductId(@Param("memberId") String memberId,
                                              @Param("productId") int productId);

}

