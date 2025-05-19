package com.example.lotteon.repository.jpa.delivery;

import com.example.lotteon.dto.order.DeliveryWrapper;
import com.example.lotteon.entity.order.Delivery;
import com.example.lotteon.entity.order.QDelivery;
import com.example.lotteon.entity.order.QDeliveryCompany;
import com.example.lotteon.entity.order.QOrder;
import com.example.lotteon.entity.order.QOrderItem;
import com.example.lotteon.entity.product.QProduct;
import com.example.lotteon.entity.seller.QSeller;
import com.example.lotteon.entity.user.QUser;
import com.example.lotteon.repository.jpa.order.OrderRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryCustomRepositoryImpl implements DeliveryCustomRepository {

  private final JPAQueryFactory query;
  private final QDelivery delivery = QDelivery.delivery;
  private final QOrder order = QOrder.order;
  private final QProduct product = QProduct.product;
  private final QOrderItem orderItem = QOrderItem.orderItem;
  private final QDeliveryCompany company = QDeliveryCompany.deliveryCompany;
  private final QSeller seller = QSeller.seller;
  private final QUser user = QUser.user;

  private final OrderRepository orderRepo;

  private List<DeliveryWrapper> toWrapperList(List<Tuple> tuples) {
    List<DeliveryWrapper> wrappers = new ArrayList<>();

    for (Tuple tuple : tuples) {
      DeliveryWrapper wrapper = DeliveryWrapper.builder()
          .tuple(tuple)
          .build();
      wrappers.add(wrapper);
    }

    return wrappers;
  }

  private JPAQuery<Tuple> selectFromJoin() {
    return query.select(delivery.deliveryNumber, company.companyName,
            order.orderNumber, order.recipientName,
            product.price
                .subtract(product.price.multiply(product.discountRate.divide(100)))
                .multiply(orderItem.amount)
                .add(product.deliveryFee)
                .sum(),
            orderItem.product.id.count(),
            product.deliveryFee.sum(),
            delivery.receiptDate)
        .from(delivery)
        .join(company).on(company.id.eq(delivery.deliveryCompany.id))
        .join(delivery.order, order)
        .join(orderItem).on(order.orderNumber.eq(orderItem.order.orderNumber))
        .join(product).on(product.id.eq(orderItem.product.id));
  }

  @Override
  public List<DeliveryWrapper> findAllDeliveries() {
    List<Tuple> tuples = selectFromJoin()
        .groupBy(order.orderNumber)
        .fetch();

    return toWrapperList(tuples);
  }

  @Override
  public Page<DeliveryWrapper> findAllAsPage(Pageable pageable) {
    List<DeliveryWrapper> wrappers = findAllDeliveries();
    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<DeliveryWrapper> findAllAsPage(String currentSellerId, Pageable pageable) {
    List<Tuple> tuples = selectFromJoin()
        .join(seller.sellerId.user, user)
        .where(seller.sellerId.user.id.eq(currentSellerId))
        .groupBy(order.orderNumber)
        .fetch();

    List<DeliveryWrapper> wrappers = toWrapperList(tuples);

    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<DeliveryWrapper> findByDeliveryNumber(String deliveryNumber, Pageable pageable) {
    Tuple tuple = selectFromJoin()
        .where(delivery.deliveryNumber.eq(deliveryNumber))
        .groupBy(order.orderNumber)
        .fetchOne();

    DeliveryWrapper wrapper = DeliveryWrapper.builder()
        .tuple(tuple)
        .build();

    List<DeliveryWrapper> wrappers = new ArrayList<>();
    wrappers.add(wrapper);

    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<DeliveryWrapper> findByDeliveryNumber(String currentSellerId, String deliveryNumber,
      Pageable pageable) {
    Tuple tuple = selectFromJoin()
        .join(seller.sellerId.user, user)
        .where(
            seller.sellerId.user.id.eq(currentSellerId)
                .and(delivery.deliveryNumber.eq(deliveryNumber)))
        .groupBy(order.orderNumber)
        .fetchOne();
    DeliveryWrapper wrapper = DeliveryWrapper.builder()
        .tuple(tuple)
        .build();

    List<DeliveryWrapper> wrappers = new ArrayList<>();
    wrappers.add(wrapper);

    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<DeliveryWrapper> findByRecipientNameAsPage(String recipientName, Pageable pageable) {
    List<Tuple> tuples = selectFromJoin()
        .where(order.recipientName.eq(recipientName))
        .groupBy(order.orderNumber)
        .fetch();

    List<DeliveryWrapper> wrappers = toWrapperList(tuples);

    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<DeliveryWrapper> findByRecipientNameAsPage(String currentSellerId,
      String recipientName, Pageable pageable) {
    List<Tuple> tuples = selectFromJoin()
        .join(seller.sellerId.user, user)
        .where(
            seller.sellerId.user.id.eq(currentSellerId).and(order.recipientName.eq(recipientName)))
        .groupBy(order.orderNumber)
        .fetch();

    List<DeliveryWrapper> wrappers = toWrapperList(tuples);

    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<DeliveryWrapper> findByOrderNumber(String orderNumber, Pageable pageable) {
    Tuple tuple = selectFromJoin()
        .where(order.orderNumber.eq(orderNumber))
        .groupBy(order.orderNumber)
        .fetchOne();

    DeliveryWrapper wrapper = DeliveryWrapper.builder()
        .tuple(tuple)
        .build();

    List<DeliveryWrapper> wrappers = new ArrayList<>();
    wrappers.add(wrapper);

    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }

  @Override
  public Page<DeliveryWrapper> findByOrderNumber(String currentSellerId, String orderNumber,
      Pageable pageable) {
    Tuple tuple = selectFromJoin()
        .where(
            seller.sellerId.user.id.eq(currentSellerId).and(order.orderNumber.eq(orderNumber)))
        .groupBy(order.orderNumber)
        .fetchOne();

    DeliveryWrapper wrapper = DeliveryWrapper.builder()
        .tuple(tuple)
        .build();

    List<DeliveryWrapper> wrappers = new ArrayList<>();
    wrappers.add(wrapper);

    return new PageImpl<>(wrappers, pageable, wrappers.size());
  }


  @Override
  public Delivery findById(String deliveryNumber) {
    return query.selectFrom(delivery)
        .join(order).on(delivery.order.orderNumber.eq(order.orderNumber))
        .where(delivery.deliveryNumber.eq(deliveryNumber))
        .groupBy(order.orderNumber)
        .fetchOne();
  }

  @Override
  public Delivery findById(String currentSellerId, String deliveryNumber) {
    return query.select(delivery)
        .join(seller.sellerId.user, user)
        .where(seller.sellerId.user.id.eq(currentSellerId)
            .and(delivery.deliveryNumber.eq(deliveryNumber)))
        .groupBy(order.orderNumber)
        .fetchOne();

  }
}
