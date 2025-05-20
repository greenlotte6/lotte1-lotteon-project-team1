package com.example.lotteon.repository.jpa.order;

import com.example.lotteon.dto.order.MypageOrderWrapper;
import com.example.lotteon.dto.order.OrderWrapper;
import com.example.lotteon.entity.order.Order;
import com.example.lotteon.entity.order.OrderItem;
import com.example.lotteon.entity.order.OrderStatus;
import com.querydsl.core.Tuple;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositoryCustom {

  Page<OrderWrapper> findAllBySellerId(String currentSellerId, Pageable pageable);

  Page<OrderWrapper> findAllOrders(Pageable pageable);

  List<OrderItem> findWithProductInfoByOrderNumberAndSellerId(String orderNumber);

  List<OrderItem> findWithProductInfoByOrderNumberAndSellerId(String currentSellerId,
      String orderNumber);

  Order findByOrderNumber(String orderNumber);

  Page<OrderWrapper> findByOrderNumber(String orderNumber, Pageable pageable);

  Page<OrderWrapper> findByOrderNumber(String currentSellerId, String orderNumber,
      Pageable pageable);

  Page<OrderWrapper> findByMemberName(String memberName, Pageable pageable);

  Page<OrderWrapper> findByMemberName(String currentSellerId, String memberName, Pageable pageable);

  Page<OrderWrapper> findByMemberId(String memberId, Pageable pageable);

  Page<OrderWrapper> findByMemberId(String currentSellerId, String memberId, Pageable pageable);

  void updateStatusByOrderNumber(String orderNumber, OrderStatus status);

  Page<MypageOrderWrapper> findOrderWrappersByUserId(String userId, Pageable pageable);

  String findLatestOrderNumber();

  // 마이페이지 전제주문내역 상세정보
  List<OrderItem> findWithProductInfoByOrderNumberAndUserId(String orderNumber, String userId);

  long count(LocalDate today);

  long countBySellerId(String sellerId, LocalDate today);

  long countByStatus(int status, LocalDate today);

  long countByStatus(String sellerId, int status, LocalDate today);

  long findTotalSales(LocalDate today);

  long findTotalSales(String sellerId, LocalDate today);

  List<Tuple> countByStatusBetween(LocalDate from, LocalDate to);

  List<Tuple> countByStatusBetween(String sellerId, LocalDate from, LocalDate to);
}
