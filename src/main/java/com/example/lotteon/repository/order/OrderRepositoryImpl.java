package com.example.lotteon.repository.order;

import com.example.lotteon.dto.order.OrderWrapper;
import com.example.lotteon.entity.order.Order;
import com.example.lotteon.entity.order.QDelivery;
import com.example.lotteon.entity.order.QOrder;
import com.example.lotteon.entity.product.QProduct;
import com.example.lotteon.entity.user.QMember;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

  private final JPAQueryFactory query;
  private final QMember member = QMember.member;
  private final QOrder order = QOrder.order;
  private final QProduct product = QProduct.product;
  private final QDelivery delivery = QDelivery.delivery;

  private List<OrderWrapper> toList(List<Tuple> tuples) {
    List<OrderWrapper> wrappers = new ArrayList<>();
    for (Tuple tuple : tuples) {
      Order order = tuple.get(0, Order.class);
      Long numberOfOrderedProducts = tuple.get(1, long.class);
      int totalPrice = tuple.get(2, int.class);
      OrderWrapper wrapper = OrderWrapper.builder()
          .order(order)
          .numberOfOrderedProducts(numberOfOrderedProducts)
          .totalPrice(totalPrice)
          .build();
      wrappers.add(wrapper);
    }
    return wrappers;
  }

  private JPAQuery<Tuple> selectFromJoin() {
    //return query.select(order,
    //        order.product.id.countDistinct().as("numberOfOrderedProducts"),//하나의 주문번호 당 주문 건수 조회
    //        product.price.subtract(
    //                (product.price.multiply(product.discountRate))
    //                    .divide(100)
    //            )
    //            .add(product.deliveryFee)
    //            .multiply(order.amount)
    //            .as("totalPrice")
    //    )
    //    .from(order)
    //    .join(product)
    //    .on(order.product.id.eq(product.id));
    return null;
  }

  @Override
  public List<Order> findByStatuesWithOrAt(String status1, String status2, LocalDate date) {
    //TODO: Impl this
    return List.of();
  }

  @Override
  public Long countByStatuesWithOrAt(String status1, String status2, LocalDate date) {
    return query.select(order.count())
        .from(order)
        .where(order.status.name.eq(status1).or(order.status.name.eq(status2)),
            order.orderDate.eq(date))
        .fetchFirst()
        .longValue();
  }

  @Override
  public Long countByStatusAt(String status, LocalDate date) {
    return query.select(order.count())
        .from(order)
        .where(order.status.name.eq(status), order.orderDate.eq(date))
        .fetchFirst();
  }

  @Override
  public Long countByDeliveryStatusAt(String status, LocalDate date) {
    return query.select(order.count())
        .from(order)
        .join(delivery)
        .on(order.delivery.deliveryNumber.eq(delivery.deliveryNumber))
        .where(order.orderDate.eq(date))
        .fetchFirst();
  }

  @Override
  public Page<OrderWrapper> getAllOrdersAndCount(Pageable pageable) {
    //List<Tuple> tuples = selectFromJoin()
    //    .groupBy(order.orderNumber)
    //    .fetch();
    //List<OrderWrapper> wrappers = toList(tuples);
    List<Order> orders = query.selectFrom(order).fetch();
    List<OrderWrapper> wrappers = new ArrayList<>();
    int numberOfOrderedProducts = 1;
    for (int i = 1; i < orders.size(); i++) {
      String prevOrderNumber = orders.get(i - 1).getOrderNumber();
      String currOrderNumber = orders.get(i).getOrderNumber();
    }
    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<OrderWrapper> findByOrderNumber(Pageable pageable, String orderNumber) {
    List<Tuple> tuples = selectFromJoin()
        .on(order.product.id.eq(product.id))
        .where(order.orderNumber.eq(orderNumber))
        .groupBy(order.orderNumber)
        .fetch();
    List<OrderWrapper> wrappers = toList(tuples);
    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<OrderWrapper> findByMemberName(Pageable pageable, String memberName) {
    List<Tuple> tuples = selectFromJoin()
        .where(order.member.name.eq(memberName))
        .groupBy(order.orderNumber)
        .fetch();
    List<OrderWrapper> wrappers = toList(tuples);
    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<OrderWrapper> findByMemberId(Pageable pageable, String memberId) {
    List<Tuple> tuples = selectFromJoin()
        .where(order.member.memberId.user.id.eq(memberId))
        .groupBy(order.orderNumber)
        .fetch();
    List<OrderWrapper> wrappers = toList(tuples);
    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }
}
