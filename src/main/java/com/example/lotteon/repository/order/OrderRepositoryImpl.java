package com.example.lotteon.repository.order;

import com.example.lotteon.entity.order.Order;
import com.example.lotteon.entity.order.QDelivery;
import com.example.lotteon.entity.order.QOrder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

  private final JPAQueryFactory query;
  private final QOrder order = QOrder.order;
  private final QDelivery delivery = QDelivery.delivery;


  @Override
  public List<Order> findByStatuesWithOrAt(String status1, String status2, LocalDate date) {
    return List.of();
  }

  @Override
  public Long countByStatuesWithOrAt(String status1, String status2, LocalDate date) {
    return query.select(order.count).from(order).where(order.status.name.eq(status1)
            .or(order.status.name.eq(status2))
            .and(order.orderDate.eq(date)))
        .fetchFirst()
        .longValue();
  }

  @Override
  public Long countByStatusAt(String status, LocalDate date) {
    return query.select(order.count())
        .from(order)
        .where(order.status.name.eq(status)
            .and(order.orderDate.eq(date)))
        .fetchFirst();
  }

  @Override
  public Long countByDeliveryStatusAt(String status, LocalDate date) {
    return query.select(order.count())
        .from(order)
        .join(delivery)
        .on(order.delivery.deliveryNumber.eq(delivery.deliveryNumber))
        .fetchFirst();
  }
}
