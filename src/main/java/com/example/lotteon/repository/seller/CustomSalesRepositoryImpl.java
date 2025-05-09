package com.example.lotteon.repository.seller;

import com.example.lotteon.entity.order.QOrder;
import com.example.lotteon.entity.order.QOrderItem;
import com.example.lotteon.entity.order.QOrderStatus;
import com.example.lotteon.entity.product.QProduct;
import com.example.lotteon.entity.seller.QSales;
import com.example.lotteon.entity.seller.QSeller;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

  @Override
  public List<Tuple> findAllAsTuples() {
    NumberExpression<Integer> confirmedPriceTotalQuery = confirmedTotalPriceQuery();

    return query.select(sales,
            order.orderNumber.countDistinct(),
            product.price
                .subtract(product.price.multiply(product.discountRate.divide(100)))
                .multiply(orderItem.amount)
                .sum(),
            confirmedPriceTotalQuery.sum(),
            countByStatusQuery(2),
            countByStatusQuery(4),
            countByStatusQuery(5),
            countByStatusQuery(6)
        )
        .from(sales)
        .join(sales.seller, seller)
        .join(order).on(sales.order.orderNumber.eq(order.orderNumber))
        .join(orderStat).on(order.status.id.eq(orderStat.id))
        .join(orderItem).on(orderItem.order.orderNumber.eq(sales.order.orderNumber))
        .join(product).on(orderItem.product.id.eq(product.id)
            .and(product.seller.sellerId.businessNumber.eq(sales.seller.sellerId.businessNumber)))
        .groupBy(sales.seller.sellerId.businessNumber)
        .fetch();

  }

  @Override
  public List<Tuple> findAllAsTuples(String sellerId) {
    NumberExpression<Integer> confirmedPriceTotalQuery = confirmedTotalPriceQuery();

    return query.select(sales,
            order.orderNumber.countDistinct(),
            product.price
                .subtract(product.price.multiply(product.discountRate.divide(100)))
                .multiply(orderItem.amount)
                .sum(),
            confirmedPriceTotalQuery.sum(),
            countByStatusQuery(2),
            countByStatusQuery(4),
            countByStatusQuery(5),
            countByStatusQuery(6)
        )
        .from(sales)
        .join(sales.seller, seller)
        .join(order).on(sales.order.orderNumber.eq(order.orderNumber))
        .join(orderStat).on(order.status.id.eq(orderStat.id))
        .join(orderItem).on(orderItem.order.orderNumber.eq(sales.order.orderNumber))
        .join(product).on(orderItem.product.id.eq(product.id)
            .and(product.seller.sellerId.businessNumber.eq(sales.seller.sellerId.businessNumber)))
        .where(sales.seller.sellerId.user.id.eq(sellerId))
        .groupBy(sales.seller.sellerId.businessNumber)
        .fetch();
  }
}
