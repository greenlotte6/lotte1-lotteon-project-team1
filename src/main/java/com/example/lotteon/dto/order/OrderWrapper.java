package com.example.lotteon.dto.order;

import com.example.lotteon.entity.order.Order;
import com.example.lotteon.entity.product.Product;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderWrapper {

  private OrderWrapper(Order order, long numberOfOrderedProducts, int totalPrice) {
    this.order = order;
    this.numberOfOrderedProducts = numberOfOrderedProducts;
    this.totalPrice = totalPrice;
  }

  private final Order order;
  private final long numberOfOrderedProducts;
  private final int totalPrice;

  public static Builder builder() {
    return new Builder();
  }

  //Builder for OrderWrapper
  public static class Builder {

    private List<Order> orders;

    private int calculatePrice(Product product, int amount) {
      int costToDiscount = product.getPrice() * (product.getDiscountRate() / 100);
      int discountedPrice = product.getPrice() - costToDiscount;
      return discountedPrice * amount + product.getDeliveryFee();
    }

    private List<OrderWrapper> buildMultipleOrders() {
      List<OrderWrapper> wrappers = new ArrayList<>();
      for (int i = 1; i < orders.size(); i++) {
        int totalPrice = 0;
        int count = 1;
        Order curr = orders.get(i);
        Order prev = orders.get(i - 1);
        totalPrice = calculatePrice(curr.getProduct(), curr.getAmount());

        //TODO:if(curr.getId() == prev.getId() && currentSellerDetail.getBusinessNumber().equals(curr.getSeller.getSellerId.getBusinessNumber)
        if (curr.getId() == prev.getId()) {
          int prevTotalPrice = calculatePrice(prev.getProduct(), prev.getAmount());
          totalPrice += prevTotalPrice;
        }

        Order aggregatedOrder = Order.builder()
            .id(curr.getId())
            .orderNumber(curr.getOrderNumber())
            .amount(curr.getAmount())
            .orderDate(curr.getOrderDate())
            .member(curr.getMember())

            .build();
        OrderWrapper wrapper = new OrderWrapper(curr, count, totalPrice);
      }
      return wrappers;
    }

    private List<OrderWrapper> buildOneOrZeroOrder() {
      List<OrderWrapper> wrappers = new ArrayList<>();
      return wrappers;
    }

    public Builder order(Order order) {
      orders.add(order);
      return this;
    }

    public List<OrderWrapper> build() {
      List<OrderWrapper> wrappers;
      if (orders.size() > 1) {
        wrappers = buildMultipleOrders();
      } else {
        wrappers = buildOneOrZeroOrder();
      }
      return wrappers;
    }
  }
}
