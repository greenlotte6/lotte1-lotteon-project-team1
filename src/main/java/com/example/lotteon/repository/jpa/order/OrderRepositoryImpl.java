package com.example.lotteon.repository.jpa.order;

import com.example.lotteon.dto.order.MypageOrderWrapper;
import com.example.lotteon.dto.order.OrderWrapper;
import com.example.lotteon.entity.order.Order;
import com.example.lotteon.entity.order.OrderItem;
import com.example.lotteon.entity.order.OrderStatus;
import com.example.lotteon.entity.order.QOrder;
import com.example.lotteon.entity.order.QOrderItem;
import com.example.lotteon.entity.order.QOrderStatus;
import com.example.lotteon.entity.product.QProduct;
import com.example.lotteon.entity.product.QProductCategory;
import com.example.lotteon.entity.seller.QSeller;
import com.example.lotteon.entity.user.QMember;
import com.example.lotteon.entity.user.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
  private final QProductCategory category = QProductCategory.productCategory;
  private final QMember member = QMember.member;
  private final QSeller seller = QSeller.seller;
  private final QUser user = QUser.user;

  private NumberExpression<Long> selectCountByStatus(int status) {
    return new CaseBuilder()
        .when(order.status.id.eq(status))
        .then(1)
        .otherwise((Integer) null)
        .count();
  }


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

  //마이페이지 코드
  @Override
  public Page<MypageOrderWrapper> findOrderWrappersByUserId(String userId, Pageable pageable) {
    // 전체 개수
    long total = query
        .select(order.count())
        .from(order)
        .join(order.member, member)
        .join(member.memberId.user, user)
        .where(user.id.eq(userId))
        .fetchOne();

    // totalPriceExpression 정의 (Long으로 받기)
    var totalPriceExpression = product.price
        .subtract(product.price.multiply(product.discountRate.divide(100))) // 할인된 가격
        .multiply(orderItem.amount) // 주문 수량에 곱하기
        .add(product.deliveryFee) // 배송비 추가
        .sum()
        .longValue();  // 결과를 Long 타입으로 변환

    // 튜플 조회 시 Long으로 받기
    List<Tuple> tuples = query
        .select(
            order.orderNumber,
            user.id,
            member.name,
            order.payment,
            order.status.id,
            order.orderDate,
            //  count를 Integer로 강제 캐스팅
            Expressions.numberTemplate(Integer.class, "count({0})", orderItem),
            totalPriceExpression.as("totalPrice"),
            product.name, //  상품명 추가
            product.image.listThumbnailLocation, //  이미지 경로
            seller.companyName, //  추가
            seller.sellerId.businessNumber // 셀러 아이디 구하기 위해서 추가 해주기
        )
        .from(order)
        .join(order.member, member)
        .join(member.memberId.user, user)
        .join(orderItem).on(order.orderNumber.eq(orderItem.order.orderNumber))
        .join(product).on(orderItem.product.id.eq(product.id))
        .join(seller).on(product.seller.sellerId.eq(seller.sellerId)) //  추가!
        .where(user.id.eq(userId))
        .groupBy(order.orderNumber)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    // MypageOrderWrapper 객체로 변환
    List<MypageOrderWrapper> content = tuples.stream()
        .map(tuple -> {
          // totalPrice는 index 7에 존재하므로 tuple.get(7)로 가져옵니다.
          Long totalPrice = tuple.get(7, Long.class);  // totalPrice를 Long으로 정확히 받아오기

          // 만약 totalPrice가 null일 수 있으면 기본값 0을 할당
          long price = (totalPrice != null) ? totalPrice : 0L;

          // OrderWrapper.builder()를 사용하여 필드 설정
          return MypageOrderWrapper.builder()
              .tuples(tuple)  // Tuple을 builder로 전달하여 처리
              .build();
        })
        .collect(Collectors.toList());

    return new PageImpl<>(content, pageable, total);
  }

  @Override
  public String findLatestOrderNumber() {
    return query.select(order.orderNumber)
        .from(order)
        .orderBy(order.orderNumber.desc())
        .limit(1)
        .fetchOne();
  }

  //마이페이지 전체 주문내역 상세정보 코드
  @Override
  public List<OrderItem> findWithProductInfoByOrderNumberAndUserId(String orderNumber,
      String userId) {
    return query
        .selectFrom(orderItem)
        .join(orderItem.order, order).fetchJoin()
        .join(order.member, member).fetchJoin()
        .join(member.memberId.user, user)
        .join(orderItem.product, product).fetchJoin()
        .join(product.seller, seller).fetchJoin()
        .where(
            order.orderNumber.eq(orderNumber),
            user.id.eq(userId)
        )
        .fetch();
  }

  @Override
  public long count(LocalDate today) {
    Long count = query
        .select(order.count())
        .from(order)
        .where(order.orderDate.eq(today))
        .fetchOne();
    return count == null ? 0 : count;
  }

  @Override
  public long countBySellerId(String sellerId, LocalDate today) {
    Long count = query
        .select(order.count())
        .from(order)
        .join(orderItem).on(orderItem.order.orderNumber.eq(order.orderNumber))
        .join(product).on(orderItem.product.id.eq(product.id))
        .join(product.seller, seller)
        .join(seller.sellerId.user, user)
        .where(user.id.eq(sellerId).and(order.orderDate.eq(today)))
        .fetchOne();
    return count == null ? 0 : count;
  }

  @Override
  public long countByStatus(int status, LocalDate today) {
    Long count = query
        .select(order.count())
        .from(order)
        .join(order.status, this.status)
        .where(this.status.id.eq(status).and(order.orderDate.eq(today)))
        .fetchOne();
    return count == null ? 0 : count;
  }

  @Override
  public long countByStatus(String sellerId, int status, LocalDate today) {
    Long count = query
        .select(order.count())
        .from(order)
        .join(orderItem).on(orderItem.order.orderNumber.eq(order.orderNumber))
        .join(product).on(orderItem.product.id.eq(product.id))
        .join(product.seller, seller)
        .join(seller.sellerId.user, user)
        .where(user.id.eq(sellerId).and(order.orderDate.eq(today))
            .and(this.status.id.eq(status)))
        .fetchOne();
    return count == null ? 0 : count;
  }

  @Override
  public long findTotalSales(LocalDate today) {
    Integer total = query.select(orderItem.totalPrice.sum())
        .from(orderItem)
        .where(orderItem.order.orderDate.eq(today))
        .fetchOne();
    return total == null ? 0 : total;
  }

  @Override
  public long findTotalSales(String sellerId, LocalDate today) {
    Integer total = query.select(orderItem.totalPrice.sum())
        .from(orderItem)
        .join(orderItem.product, product)
        .join(product.seller, seller)
        .join(seller.sellerId.user, user)
        .where(user.id.eq(sellerId).and(orderItem.order.orderDate.eq(today)))
        .fetchOne();
    return total == null ? 0 : total;
  }

  //TODO: Currently working position
  @Override
  public List<Tuple> countByStatusBetween(LocalDate from, LocalDate to) {
    return query.select(order.orderDate,
            order.status.id.when(2).then(1).otherwise(0).sum(),
            order.status.id.when(3).then(1).otherwise(0).sum(),
            order.status.id.when(4).then(1).otherwise(0).sum()
        )
        .from(order)
        .where(order.orderDate.between(from, to))
        .groupBy(order.orderDate)
        .fetch();
  }

  @Override
  public List<Tuple> countByStatusBetween(String sellerId, LocalDate from,
      LocalDate to) {
    return query.select(order.orderDate,
            order.status.id.when(2).then(1).otherwise(0).sum(),
            order.status.id.when(3).then(1).otherwise(0).sum(),
            order.status.id.when(4).then(1).otherwise(0).sum())
        .from(order)
        .join(orderItem.order, order)
        .join(orderItem.product, product)
        .join(product.seller, seller)
        .join(seller.sellerId.user, user)
        .where(user.id.eq(sellerId).and(order.orderDate.between(from, to)))
        .groupBy(order.orderDate)
        .fetch();
  }
}
