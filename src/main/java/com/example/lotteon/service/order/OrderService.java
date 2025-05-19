package com.example.lotteon.service.order;

import com.example.lotteon.dto.order.MypageOrderWrapper;
import com.example.lotteon.dto.order.OrderItemDTO;
import com.example.lotteon.dto.order.OrderSheet;
import com.example.lotteon.dto.order.OrderStatusDTO;
import com.example.lotteon.dto.order.OrderWrapper;
import com.example.lotteon.entity.order.Order;
import com.example.lotteon.entity.order.OrderItem;
import com.example.lotteon.entity.order.OrderStatus;
import com.example.lotteon.repository.jpa.order.OrderItemRepository;
import com.example.lotteon.repository.jpa.order.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderItemRepository orderItemRepo;
  private final OrderRepository repo;
  private final ModelMapper mapper;

  public Page<OrderWrapper> getAllOrders(Pageable pageable) {
    return repo.findAllOrders(pageable);
  }

  public Page<OrderWrapper> getAllOrdersBySellerId(String currentSellerId, Pageable pageable) {
    return repo.findAllBySellerId(currentSellerId, pageable);
  }

  public List<OrderItemDTO> getOrderDetail(String orderNumber) {
    List<OrderItem> entities = repo.findWithProductInfoByOrderNumberAndSellerId(orderNumber);

    List<OrderItemDTO> items = new ArrayList<>();

    for (OrderItem entity : entities) {
      OrderItemDTO item = mapper.map(entity, OrderItemDTO.class);
      items.add(item);
    }

    return items;
  }

  public List<OrderItemDTO> getOrderDetail(String currentSellerId, String orderNumber) {
    List<OrderItem> entities = repo.findWithProductInfoByOrderNumberAndSellerId(currentSellerId,
        orderNumber);

    List<OrderItemDTO> items = new ArrayList<>();

    for (OrderItem entity : entities) {
      OrderItemDTO item = mapper.map(entity, OrderItemDTO.class);
      items.add(item);
    }

    return items;
  }

  public Order searchByOrderNumber(String orderNumber) {
    return repo.findByOrderNumber(orderNumber);
  }

  public Page<OrderWrapper> searchByOrderNumber(String orderNumber, Pageable pageable) {
    return repo.findByOrderNumber(orderNumber, pageable);
  }

  public Page<OrderWrapper> searchByOrderNumber(String currentSellerId, String orderNumber,
      Pageable pageable) {
    return repo.findByOrderNumber(currentSellerId, orderNumber, pageable);
  }

  public Page<OrderWrapper> searchByMemberName(String memberName, Pageable pageable) {
    return repo.findByMemberName(memberName, pageable);
  }

  public Page<OrderWrapper> searchByMemberName(String currentSellerId, String memberName,
      Pageable pageable) {
    return repo.findByMemberName(currentSellerId, memberName, pageable);
  }

  public Page<OrderWrapper> searchByMemberId(String memberId, Pageable pageable) {
    return repo.findByMemberId(memberId, pageable);
  }

  public Page<OrderWrapper> searchByMemberId(String currentSellerId, String memberId,
      Pageable pageable) {
    return repo.findByMemberId(currentSellerId, memberId, pageable);
  }

  public void updateStatusByOrderNumber(String orderNumber, OrderStatusDTO status) {
    OrderStatus entity = mapper.map(status, OrderStatus.class);
    repo.updateStatusByOrderNumber(orderNumber, entity);
  }

  // 마이페이지 코드
  public Page<MypageOrderWrapper> findOrderWrappersByUserId(String userId, Pageable pageable) {
    return repo.findOrderWrappersByUserId(userId, pageable);
  }

  public String getLatestOrderNumber() {
    String lastNumberString = repo.findLatestOrderNumber();
    int latestNumber = Integer.parseInt(lastNumberString) + 1;
    return String.valueOf(latestNumber);
  }

  @Transactional
  public void placeOrder(OrderSheet orderSheet) {
    Order order = mapper.map(orderSheet.getOrder(), Order.class);
    repo.save(order);
    repo.flush();
    List<OrderItem> orderItems = orderSheet.getOrderItems().stream().map(((orderItemDTO) -> {
      return mapper.map(orderItemDTO, OrderItem.class);
    })).toList();
    orderItemRepo.saveAll(orderItems);
    orderItemRepo.flush();
  }
}
