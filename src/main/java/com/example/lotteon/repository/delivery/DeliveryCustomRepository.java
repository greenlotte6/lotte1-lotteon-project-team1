package com.example.lotteon.repository.delivery;

import com.example.lotteon.entity.order.Delivery;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryCustomRepository {

  Delivery findByOrderNumber(String orderNumber);
}
