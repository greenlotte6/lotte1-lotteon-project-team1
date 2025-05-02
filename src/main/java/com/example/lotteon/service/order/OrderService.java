package com.example.lotteon.service.order;

import com.example.lotteon.dto.order.OrderStatusDTO;
import com.example.lotteon.dto.order.OrderWrapper;
import com.example.lotteon.entity.order.DeliveryStatus;
import com.example.lotteon.entity.order.Order;
import com.example.lotteon.repository.order.OrderRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository repo;

  public long countPaymentWaitingOrdersAt(LocalDate date) {
    return repo.countByStatusAt(OrderStatusDTO.STATUS_PAYMENT_WAITING, date);
  }

  public long countDeliveryReadyOrdersAt(LocalDate date) {
    return repo.countByDeliveryStatusAt(DeliveryStatus.STATUS_READY, date);
  }

  public long countCancelRequestedOrdersAt(LocalDate date) {
    return repo.countByStatusAt(OrderStatusDTO.STATUS_CANCEL_REQUESTED, date);
  }

  public long countExchangeRequestedOrdersAt(LocalDate date) {
    return repo.countByStatusAt(OrderStatusDTO.STATUS_EXCHANGE_REQUESTED, date);
  }

  public long countRefundRequestedOrdersAt(LocalDate date) {
    return repo.countByStatusAt(OrderStatusDTO.STATUS_REFUND_REQUESTED, date);
  }

  public long countValidOrdersAt(LocalDate date) {
    return repo.countByStatuesWithOrAt(OrderStatusDTO.STATUS_PAID,
        OrderStatusDTO.STATUS_EXCHANGE_REQUESTED, date);
  }

  public long calculatePriceOfValidOrdersAt(LocalDate date) {
    List<Order> orders = repo.findByStatuesWithOrAt(OrderStatusDTO.STATUS_PAID,
        OrderStatusDTO.STATUS_EXCHANGE_REQUESTED, date);
    long price = 0;
    for (Order order : orders) {
      price += order.getProduct().getPrice();
    }

    return price;
  }

  public Page<OrderWrapper> getAllOrders(Pageable pageable) {
    return repo.getAllOrdersAndCount(pageable);
  }

  public Page<OrderWrapper> searchByOrderNumber(Pageable pageable, String orderNumber) {
    return repo.findByOrderNumber(pageable, orderNumber);
  }

  public Page<OrderWrapper> searchByMemberName(Pageable pageable, String memberName) {
    return repo.findByMemberName(pageable, memberName);
  }

  public Page<OrderWrapper> searchByMemberId(Pageable pageable, String memberId) {
    return repo.findByMemberId(pageable, memberId);
  }
}
