package com.example.lotteon.service.delivery;

import com.example.lotteon.dto.order.DeliveryDTO;
import com.example.lotteon.dto.order.DeliveryOrderItemWrapper;
import com.example.lotteon.dto.order.DeliveryWrapper;
import com.example.lotteon.dto.order.OrderItemDTO;
import com.example.lotteon.entity.order.Delivery;
import com.example.lotteon.entity.order.OrderItem;
import com.example.lotteon.repository.jpa.delivery.DeliveryRepository;
import com.example.lotteon.repository.jpa.order.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final OrderRepository orderRepo;
  private final DeliveryRepository repo;
  private final ModelMapper mapper;

  public Page<DeliveryWrapper> list(Pageable pageable) {
    return repo.findAllAsPage(pageable);
  }

  public Page<DeliveryWrapper> list(String currentSellerId, Pageable pageable) {
    return repo.findAllAsPage(currentSellerId, pageable);
  }

  public Page<DeliveryWrapper> searchByDeliveryNumber(String deliveryNumber, Pageable pageable) {
    return repo.findByDeliveryNumber(deliveryNumber, pageable);
  }

  public Page<DeliveryWrapper> searchByDeliveryNumber(String currentSellerId, String deliveryNumber,
      Pageable pageable) {
    return repo.findByDeliveryNumber(currentSellerId, deliveryNumber, pageable);
  }

  public Page<DeliveryWrapper> searchByRecipientName(String recipientName, Pageable pageable) {
    return repo.findByRecipientNameAsPage(recipientName, pageable);
  }

  public Page<DeliveryWrapper> searchByRecipientName(String currentSellerId, String recipientName,
      Pageable pageable) {
    return repo.findByRecipientNameAsPage(currentSellerId, recipientName, pageable);
  }

  public Page<DeliveryWrapper> searchByOrderNumber(String orderNumber, Pageable pageable) {
    return repo.findByOrderNumber(orderNumber, pageable);
  }

  public Page<DeliveryWrapper> searchByOrderNumber(String currentSellerId, String orderNumber,
      Pageable pageable) {
    return repo.findByOrderNumber(currentSellerId, orderNumber, pageable);
  }

  public DeliveryOrderItemWrapper getDetailOf(String deliveryNumber) {
    Delivery entity = repo.findById(deliveryNumber);
    DeliveryDTO delivery = mapper.map(entity, DeliveryDTO.class);
    List<OrderItem> entities = orderRepo.findWithProductInfoByOrderNumberAndSellerId(
        delivery.getOrder().getOrderNumber());
    List<OrderItemDTO> items = new ArrayList<>();

    for (OrderItem orderItem : entities) {
      OrderItemDTO orderItemDTO = mapper.map(orderItem, OrderItemDTO.class);
      items.add(orderItemDTO);
    }

    return DeliveryOrderItemWrapper.builder()
        .delivery(delivery)
        .orderItems(items)
        .build();
  }

  public DeliveryOrderItemWrapper getDetailOf(String currentSellerId, String deliveryNumber) {
    Delivery entity = repo.findById(deliveryNumber);
    DeliveryDTO delivery = mapper.map(entity, DeliveryDTO.class);
    List<OrderItem> entities = orderRepo.findWithProductInfoByOrderNumberAndSellerId(
        currentSellerId,
        delivery.getOrder().getOrderNumber());

    List<OrderItemDTO> items = new ArrayList<>();
    for (OrderItem orderItem : entities) {
      OrderItemDTO orderItemDTO = mapper.map(orderItem, OrderItemDTO.class);
      items.add(orderItemDTO);
    }

    return DeliveryOrderItemWrapper.builder()
        .delivery(delivery)
        .orderItems(items)
        .build();
  }

  public void save(DeliveryDTO dto) {
    Delivery delivery = mapper.map(dto, Delivery.class);
    repo.save(delivery);
  }

}
