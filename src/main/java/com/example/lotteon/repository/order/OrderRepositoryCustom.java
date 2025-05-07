package com.example.lotteon.repository.order;

import com.example.lotteon.dto.order.OrderWrapper;
import com.example.lotteon.entity.order.Order;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositoryCustom {

  /**
   * 2개의 상태 중 적어도 하나를 만족하면서 특정 날짜에 해당하는 데이터를 조회
   *
   * @param status1 조건에 사용되는 주문 상태 1
   * @param status2 조건에 사용되는 주문 상태 2
   * @param date    조건에 사용되는 날짜
   * @return 조건을 만족하는 주문 데이터들의 리스트
   */
  List<Order> findByStatuesWithOrAt(String status1, String status2, LocalDate date);

  /**
   * 2개의 상태 중 적어도 하나를 만족하면서 특정 날짜에 해당하는 데이터의 개수를 조회
   *
   * @param status1 조건에 사용되는 주문 상태 1
   * @param status2 조건에 사용되는 주문 상태 2
   * @param date    조건에 사용되는 날짜
   * @return 조건을 만족하는 주문 데이터들의 개수
   */
  Long countByStatuesWithOrAt(String status1, String status2, LocalDate date);

  Long countByStatusAt(String status, LocalDate date);

  Long countByDeliveryStatusAt(String status, LocalDate date);

  Page<OrderWrapper> findAllBySellerId(String currentSellerId, Pageable pageable);

  Page<OrderWrapper> findAllOrders(Pageable pageable);

  Order findByOrderNumber(String orderNumber);

  Page<OrderWrapper> findByOrderNumber(String orderNumber, Pageable pageable);

  Page<OrderWrapper> findByOrderNumber(String currentSellerId, String orderNumber,
      Pageable pageable);

  Page<OrderWrapper> findByMemberName(String memberName, Pageable pageable);

  Page<OrderWrapper> findByMemberName(String currentSellerId, String memberName, Pageable pageable);

  Page<OrderWrapper> findByMemberId(String memberId, Pageable pageable);

  Page<OrderWrapper> findByMemberId(String currentSellerId, String memberId, Pageable pageable);

}
