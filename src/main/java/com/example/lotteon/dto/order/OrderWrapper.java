package com.example.lotteon.dto.order;

import com.example.lotteon.entity.order.Order;
import com.example.lotteon.entity.order.OrderStatus;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.entity.user.MemberId;
import com.example.lotteon.entity.user.User;
import com.google.gson.annotations.SerializedName;
import com.querydsl.core.Tuple;
import java.time.LocalDate;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderWrapper {

  @SerializedName("order")
  private final Order order;

  @SerializedName("itemCount")
  private final long itemCount;

  @SerializedName("total_price")
  private final int totalPrice;

  private OrderWrapper(Order order, long itemCount, int totalPrice) {
    this.order = order;
    this.itemCount = itemCount;
    this.totalPrice = totalPrice;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Tuple tuple;

    public Builder tuples(Tuple tuple) {
      this.tuple = tuple;
      return this;
    }

    public OrderWrapper build() {
      String orderNumber = tuple.get(0, String.class);
      String userId = tuple.get(1, String.class);
      String memberName = tuple.get(2, String.class);
      String payment = tuple.get(3, String.class);
      int statusId = tuple.get(4, int.class);
      LocalDate orderDate = tuple.get(5, LocalDate.class);
      int totalPrice = tuple.get(6, int.class);
      long itemCount = tuple.get(7, Long.class);

      User user = User.builder().id(userId).build();
      MemberId memberId = MemberId.builder().user(user).build();
      Member member = Member.builder().memberId(memberId).name(memberName).build();
      OrderStatus status = OrderStatus.builder().id(statusId).build();
      Order order = Order.builder()
          .orderNumber(orderNumber)
          .member(member)
          .payment(payment)
          .status(status)
          .orderDate(orderDate)
          .build();

      return new OrderWrapper(order, itemCount, totalPrice);
    }
  }
}
