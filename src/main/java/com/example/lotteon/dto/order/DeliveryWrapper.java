package com.example.lotteon.dto.order;

import com.querydsl.core.Tuple;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DeliveryWrapper {

  private final DeliveryDTO delivery;

  private final int itemCount;

  private final int totalPrice;

  private final int totalDeliveryFee;

  private final int status;

  private DeliveryWrapper(DeliveryDTO delivery, int itemCount, int totalPrice,
      int totalDeliveryFee, int status) {
    this.delivery = delivery;
    this.itemCount = itemCount;
    this.totalPrice = totalPrice;
    this.totalDeliveryFee = totalDeliveryFee;
    this.status = status;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Tuple tuple;

    private OrderDTO createOrder(String recipientName, String orderNumber) {
      return OrderDTO.builder()
          .orderNumber(orderNumber)
          .recipientName(recipientName)
          .build();
    }

    private DeliveryCompanyDTO createDeliveryCompany(String deliveryCompanyName) {
      return DeliveryCompanyDTO.builder()
          .companyName(deliveryCompanyName)
          .build();
    }

    private int calculateStatus(Date receiptDate) {
      ZoneId zoneId = ZoneId.systemDefault();
      LocalDateTime localNow = LocalDateTime.now();
      LocalDateTime localReceiveDate = LocalDateTime.ofInstant(receiptDate.toInstant(), zoneId);
      long daysBetween = ChronoUnit.DAYS.between(localReceiveDate, localNow);
      if (daysBetween == 3) {
        return 5;
      } else if (daysBetween == 1) {
        return 4;
      }
      return 3;
    }

    public Builder tuple(Tuple tuple) {
      this.tuple = tuple;
      return this;
    }

    public DeliveryWrapper build() {
      // Tuple 에서 값 추출
      String deliveryNumber = tuple.get(0, String.class);
      String deliveryCompanyName = tuple.get(1, String.class);
      String orderNumber = tuple.get(2, String.class);
      String recipientName = tuple.get(3, String.class);
      int totalPrice = tuple.get(4, Number.class).intValue();
      int itemCount = tuple.get(5, Number.class).intValue();
      int totalDeliveryFee = tuple.get(6, Number.class).intValue();

      //접수일
      LocalDateTime dateTime = tuple.get(7, LocalDateTime.class);
      Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

      // 배송 상태
      int status = calculateStatus(date);

      OrderDTO order = createOrder(recipientName, orderNumber);
      DeliveryCompanyDTO deliveryCompany = createDeliveryCompany(deliveryCompanyName);

      DeliveryDTO delivery = DeliveryDTO.builder()
          .deliveryNumber(deliveryNumber)
          .order(order)
          .deliveryCompany(deliveryCompany)
          .receiptDate(date)
          .build();

      return new DeliveryWrapper(delivery, itemCount, totalPrice, totalDeliveryFee, status);
    }
  }
}
