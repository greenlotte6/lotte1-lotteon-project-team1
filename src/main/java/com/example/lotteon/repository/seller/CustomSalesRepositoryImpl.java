package com.example.lotteon.repository.seller;

import com.example.lotteon.entity.order.QOrder;
import com.example.lotteon.entity.order.QOrderItem;
import com.example.lotteon.entity.product.QProduct;
import com.example.lotteon.entity.seller.QSales;
import com.example.lotteon.entity.seller.QSeller;
import com.example.lotteon.entity.seller.Sales;
import com.querydsl.core.Tuple;
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
  private final QOrderItem orderItem = QOrderItem.orderItem;
  private final QProduct product = QProduct.product;

  @Override
  public List<Sales> findAll() {
    List<Tuple> tuples = query.select(seller.sellerId.businessNumber,
            product.price
                .subtract(product.price.multiply(product.discountRate.divide(100)))
                .multiply(orderItem.amount)
                .sum()
        )
        .from(sales)
        .join(sales.seller, seller)
        .join(sales.order, order)
        .join(orderItem).on(orderItem.order.orderNumber.eq(sales.order.orderNumber))
        .join(product).on(orderItem.product.id.eq(product.id)
            .and(product.seller.sellerId.businessNumber.eq(sales.seller.sellerId.businessNumber)))
        .groupBy(sales.seller.sellerId.businessNumber)
        .fetch();
    //TODO: Currently working at here
    return List.of();
  }
}
