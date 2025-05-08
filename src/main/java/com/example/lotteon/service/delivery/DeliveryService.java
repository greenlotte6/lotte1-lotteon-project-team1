package com.example.lotteon.service.delivery;

import com.example.lotteon.dto.order.DeliveryDTO;
import com.example.lotteon.entity.order.Delivery;
import com.example.lotteon.repository.delivery.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final DeliveryRepository repo;
  private final ModelMapper mapper;

  public void save(DeliveryDTO dto) {
    Delivery delivery = mapper.map(dto, Delivery.class);
    repo.save(delivery);
  }

}
