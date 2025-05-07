package com.example.lotteon.service.delivery;

import com.example.lotteon.entity.order.Delivery;
import com.example.lotteon.repository.delivery.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final DeliveryRepository repo;

  public Delivery getByOrderNumber(String orderNumber) {
    return repo.findByOrderNumber(orderNumber);
  }

}
