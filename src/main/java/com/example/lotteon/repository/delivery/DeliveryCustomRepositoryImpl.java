package com.example.lotteon.repository.delivery;

import com.example.lotteon.entity.order.Delivery;
import com.example.lotteon.entity.order.QDelivery;
import com.example.lotteon.entity.order.QOrder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryCustomRepositoryImpl implements DeliveryCustomRepository {

  private final JPAQueryFactory query;
  private final QOrder order = QOrder.order;
  private final QDelivery delivery = QDelivery.delivery;

  @Override
  public Delivery findByOrderNumber(String orderNumber) {
    return query.selectFrom(delivery)
        .join(delivery.order, order)
        .where(delivery.order.orderNumber.eq(orderNumber))
        .fetchOne();
  }
}
