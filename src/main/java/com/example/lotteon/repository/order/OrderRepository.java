package com.example.lotteon.repository.order;

import com.example.lotteon.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, OrderRepositoryCustom {

}
