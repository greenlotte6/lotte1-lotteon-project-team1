package com.example.lotteon.repository.jpa.order;

import com.example.lotteon.dto.order.MypageOrderWrapper;
import com.example.lotteon.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, OrderRepositoryCustom {

  //마이페이지 코드
  Page<MypageOrderWrapper> findOrderWrappersByUserId(String userId, Pageable pageable);
}
