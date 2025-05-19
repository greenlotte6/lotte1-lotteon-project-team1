package com.example.lotteon.repository.jpa.seller;

import com.example.lotteon.entity.order.QOrder;
import com.example.lotteon.entity.order.QOrderItem;
import com.example.lotteon.entity.order.QOrderStatus;
import com.example.lotteon.entity.product.QProduct;
import com.example.lotteon.entity.seller.QSales;
import com.example.lotteon.entity.seller.QSeller;
import com.example.lotteon.entity.user.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomSalesRepositoryImpl implements CustomSalesRepository {

  private final JPAQueryFactory query;
  private final QSales sales = QSales.sales;
  private final QSeller seller = QSeller.seller;
  private final QOrder order = QOrder.order;
  private final QOrderStatus orderStat = QOrderStatus.orderStatus;
  private final QOrderItem orderItem = QOrderItem.orderItem;
  private final QProduct product = QProduct.product;
  private final QUser user = QUser.user;

  private NumberExpression<Integer> totalPriceQuery() {
    return product.price
        .subtract(product.price.multiply(product.discountRate.divide(100)))
        .multiply(orderItem.amount)
        .sum();
  }

  private NumberExpression<Integer> confirmedTotalPriceQuery() {
    return new CaseBuilder().when(
            order.status.id.eq(6))
        .then(product.price
            .subtract(product.price.multiply(product.discountRate.divide(100)))
            .multiply(orderItem.amount))
        .otherwise(0);
  }

  private NumberExpression<Integer> countByStatusQuery(int statusId) {
    return new CaseBuilder().when(
            order.status.id.eq(statusId))
        .then(1)
        .otherwise(0);
  }

  private JPAQuery<Tuple> selectFromJoin() {
    NumberExpression<Integer> confirmedPriceTotalQuery = confirmedTotalPriceQuery();
    return query.select(sales,
            order.orderNumber.countDistinct(),
            totalPriceQuery(),
            confirmedPriceTotalQuery.sum(),
            countByStatusQuery(2),
            countByStatusQuery(4),
            countByStatusQuery(5),
            countByStatusQuery(6)
        )
        .from(sales)
        .join(sales.seller, seller)
        .join(user).on(seller.sellerId.user.id.eq(user.id))
        .join(order).on(sales.order.orderNumber.eq(order.orderNumber))
        .join(orderStat).on(order.status.id.eq(orderStat.id))
        .join(orderItem).on(orderItem.order.orderNumber.eq(sales.order.orderNumber))
        .join(product).on(orderItem.product.id.eq(product.id)
            .and(product.seller.sellerId.businessNumber.eq(sales.seller.sellerId.businessNumber)));
  }

  private OrderSpecifier<?> determineSorting(String sort) {
    NumberExpression<Integer> totalPrice = confirmedTotalPriceQuery();
    if (sort.equals("asc")) {
      return totalPrice.asc();
    } else {
      return totalPrice.desc();
    }
  }

  @Override
  public List<Tuple> findAllAsTuples(String sort) {
    OrderSpecifier<?> orderSpecifier = determineSorting(sort);
    return selectFromJoin()
        .groupBy(sales.seller.sellerId.businessNumber)
        .orderBy(orderSpecifier)
        .fetch();

  }

  @Override
  public List<Tuple> findAllAsTuples(String sort, String sellerId) {
    OrderSpecifier<?> orderSpecifier = determineSorting(sort);
    return selectFromJoin()
        .where(user.id.eq(sellerId))
        .groupBy(sales.seller.sellerId.businessNumber)
        .orderBy(orderSpecifier)
        .fetch();
  }

  @Override
  public List<Tuple> findAllAsTuplesWithSortDaily(String sort) {
    LocalDate now = LocalDate.now();
    OrderSpecifier<?> sortingExpression = determineSorting(sort);
    return selectFromJoin()
        .where(sales.order.orderDate.eq(now))
        .groupBy(sales.seller.sellerId.businessNumber)
        .orderBy(sortingExpression)
        .fetch();
  }

  @Override
  public List<Tuple> findAllAsTuplesWithSortDaily(String sort, String sellerId) {
    LocalDate now = LocalDate.now();
    OrderSpecifier<?> sortingExpression = determineSorting(sort);
    return selectFromJoin()
        .where(sales.order.orderDate.eq(now).and(user.id.eq(sellerId)))
        .groupBy(sales.seller.sellerId.businessNumber)
        .orderBy(sortingExpression)
        .fetch();
  }

  @Override
  public List<Tuple> findAllAsTuplesWithSortWeekly(String sort) {
    LocalDate now = LocalDate.now();
    LocalDate from = now.minusWeeks(1);
    OrderSpecifier<?> sortingExpression = determineSorting(sort);
    return selectFromJoin()
        .where(sales.order.orderDate.between(from, now))
        .groupBy(sales.seller.sellerId.businessNumber)
        .orderBy(sortingExpression)
        .fetch();
  }

  @Override
  public List<Tuple> findAllAsTuplesWithSortWeekly(String sort, String sellerId) {
    LocalDate now = LocalDate.now();
    LocalDate from = now.minusWeeks(1);
    OrderSpecifier<?> sortingExpression = determineSorting(sort);
    return selectFromJoin()
        .where(user.id.eq(sellerId).and(sales.order.orderDate.between(from, now)))
        .groupBy(sales.seller.sellerId.businessNumber)
        .orderBy(sortingExpression)
        .fetch();
  }

  @Override
  public List<Tuple> findAllAsTuplesWithSortMonthly(String sort) {
    LocalDate now = LocalDate.now();
    LocalDate from = now.minusMonths(1)
        .withDayOfMonth(1);
    LocalDate to = now.minusMonths(1)
        .withDayOfMonth(now.minusMonths(1).lengthOfMonth());
    OrderSpecifier<?> orderSpecifier = determineSorting(sort);
    return selectFromJoin()
        .where(sales.order.orderDate.between(from, to))
        .groupBy(sales.seller.sellerId.businessNumber)
        .orderBy(orderSpecifier)
        .fetch();
  }

  @Override
  public List<Tuple> findAllAsTuplesWithSortMonthly(String sort, String sellerId) {
    LocalDate now = LocalDate.now();
    LocalDate from = now.minusMonths(1)
        .withDayOfMonth(1);
    LocalDate to = now.minusMonths(1)
        .withDayOfMonth(now.minusMonths(1).lengthOfMonth());
    OrderSpecifier<?> orderSpecifier = determineSorting(sort);
    return selectFromJoin()
        .where(user.id.eq(sellerId).and(sales.order.orderDate.between(from, to)))
        .groupBy(sales.seller.sellerId.businessNumber)
        .orderBy(orderSpecifier)
        .fetch();
  }
}
