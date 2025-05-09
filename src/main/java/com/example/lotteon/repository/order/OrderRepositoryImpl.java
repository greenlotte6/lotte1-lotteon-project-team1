package com.example.lotteon.repository.order;

import com.example.lotteon.dto.order.OrderWrapper;
import com.example.lotteon.entity.order.Order;
import com.example.lotteon.entity.order.OrderItem;
import com.example.lotteon.entity.order.OrderStatus;
import com.example.lotteon.entity.order.QDelivery;
import com.example.lotteon.entity.order.QOrder;
import com.example.lotteon.entity.order.QOrderItem;
import com.example.lotteon.entity.order.QOrderStatus;
import com.example.lotteon.entity.product.QProduct;
import com.example.lotteon.entity.seller.QSeller;
import com.example.lotteon.entity.user.QMember;
import com.example.lotteon.entity.user.QUser;
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
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

  private final JPAQueryFactory query;
  private final QOrder order = QOrder.order;
  private final QOrderStatus status = QOrderStatus.orderStatus;
  private final QOrderItem orderItem = QOrderItem.orderItem;
  private final QProduct product = QProduct.product;
  private final QDelivery delivery = QDelivery.delivery;
  private final QMember member = QMember.member;
  private final QSeller seller = QSeller.seller;
  private final QUser user = QUser.user;

  private List<OrderWrapper> toList(List<Tuple> tuples) {
    List<OrderWrapper> wrappers = new ArrayList<>();
    for (Tuple tuple : tuples) {
      OrderWrapper wrapper = OrderWrapper.builder().tuples(tuple).build();
      wrappers.add(wrapper);
    }
    return wrappers;
  }

  private JPAQuery<Tuple> selectFromJoin() {
    return query
        .select(
            order.orderNumber,
            member.memberId.user.id,
            member.name,
            order.payment,
            order.status.id,
            order.orderDate,
            product.price
                .subtract(product.price.multiply(product.discountRate.divide(100)))
                .multiply(orderItem.amount)
                .add(product.deliveryFee)
                .sum(),
            orderItem.product.id.count()
        )
        .from(order)
        .join(orderItem).on(order.orderNumber.eq(orderItem.order.orderNumber))
        .join(product).on(orderItem.product.id.eq(product.id))
        .join(product.seller, seller)
        .join(seller.sellerId.user, user)
        .join(order.member, member)
        .join(order.status, status);
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
        .on(delivery.order.orderNumber.eq(order.orderNumber))
        .where(order.orderDate.eq(date))
        .fetchFirst();
  }

  @Override
  public Page<OrderWrapper> findAllOrders(Pageable pageable) {
    List<Tuple> tuples = selectFromJoin()
        .groupBy(order.orderNumber)
        .fetch();

    List<OrderWrapper> wrappers = toList(tuples);

    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<OrderWrapper> findAllBySellerId(String currentSellerId, Pageable pageable) {
    List<Tuple> tuples = selectFromJoin()
        .where(user.id.eq(currentSellerId))
        .groupBy(order.orderNumber)
        .fetch();

    List<OrderWrapper> wrappers = toList(tuples);

    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public List<OrderItem> findWithProductInfoByOrderNumberAndSellerId(String orderNumber) {
    return query
        .selectFrom(orderItem)
        .join(orderItem.order, order).fetchJoin()
        .join(orderItem.product, product).fetchJoin()
        .join(product.seller, seller)
        .where(order.orderNumber.eq(orderNumber))
        .fetch();
  }

  @Override
  public List<OrderItem> findWithProductInfoByOrderNumberAndSellerId(String currentSellerId,
      String orderNumber) {
    return query
        .selectFrom(orderItem)
        .join(orderItem.order, order).fetchJoin()
        .join(orderItem.product, product).fetchJoin()
        .join(product.seller, seller)
        .where(
            order.orderNumber.eq(orderNumber),
            seller.sellerId.user.id.eq(currentSellerId) // 중첩된 @Embeddable 구조
        )
        .fetch();
  }

  @Override
  public Order findByOrderNumber(String orderNumber) {
    return query.selectFrom(order).where(order.orderNumber.eq(orderNumber)).fetchOne();
  }

  @Override
  public Page<OrderWrapper> findByOrderNumber(String orderNumber, Pageable pageable) {
    List<Tuple> tuples = selectFromJoin()
        .where(order.orderNumber.eq(orderNumber))
        .groupBy(order.orderNumber)
        .fetch();

    List<OrderWrapper> wrappers = toList(tuples);

    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<OrderWrapper> findByOrderNumber(String currentSellerId, String orderNumber,
      Pageable pageable) {
    List<Tuple> tuples = selectFromJoin()
        .where(user.id.eq(currentSellerId).and(order.orderNumber.eq(orderNumber)))
        .groupBy(order.orderNumber)
        .fetch();
    List<OrderWrapper> wrappers = toList(tuples);
    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<OrderWrapper> findByMemberName(String memberName, Pageable pageable) {
    List<Tuple> tuples = selectFromJoin()
        .where(member.name.eq(memberName))
        .groupBy(order.orderNumber)
        .fetch();

    List<OrderWrapper> wrappers = toList(tuples);

    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<OrderWrapper> findByMemberName(String currentSellerId, String memberName,
      Pageable pageable) {
    List<Tuple> tuples = selectFromJoin()
        .where(user.id.eq(currentSellerId).and(member.name.eq(memberName)))
        .groupBy(order.orderNumber)
        .fetch();
    List<OrderWrapper> wrappers = toList(tuples);
    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<OrderWrapper> findByMemberId(String memberId, Pageable pageable) {
    List<Tuple> tuples = selectFromJoin()
        .where(member.memberId.user.id.eq(memberId))
        .groupBy(order.orderNumber)
        .fetch();

    List<OrderWrapper> wrappers = toList(tuples);

    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<OrderWrapper> findByMemberId(String currentSellerId, String memberId,
      Pageable pageable) {
    List<Tuple> tuples = selectFromJoin()
        .where(user.id.eq(currentSellerId).and(member.memberId.user.id.eq(memberId)))
        .groupBy(order.orderNumber)
        .fetch();
    List<OrderWrapper> wrappers = toList(tuples);
    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  @Transactional
  public void updateStatusByOrderNumber(String orderNumber, OrderStatus status) {
    query.update(order)
        .set(order.status, status)
        .where(order.orderNumber.eq(orderNumber))
        .execute();
  }
}
