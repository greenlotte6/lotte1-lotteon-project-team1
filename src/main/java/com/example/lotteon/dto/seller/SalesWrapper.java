package com.example.lotteon.dto.seller;

import com.example.lotteon.entity.seller.Sales;
import com.querydsl.core.Tuple;
import lombok.Getter;
import org.modelmapper.ModelMapper;

@Getter
public class SalesWrapper {

  private final SalesDTO sales;
  private final int paidOrderCount;
  private final int onDeliveryCount;
  private final int deliveredOrderCount;
  private final int purchaseConfirmedCount;
  private final int orderCount;
  private final int totalPrice;
  private final int confirmedTotalPrice;

  private SalesWrapper(SalesDTO sales, int orderCount, int totalPrice, int confirmedTotalPrice,
      int paidOrderCount, int onDeliveryCount, int deliveredOrderCount,
      int purchaseConfirmedCount) {
    this.sales = sales;
    this.orderCount = orderCount;
    this.totalPrice = totalPrice;
    this.confirmedTotalPrice = confirmedTotalPrice;
    this.paidOrderCount = paidOrderCount;
    this.onDeliveryCount = onDeliveryCount;
    this.deliveredOrderCount = deliveredOrderCount;
    this.purchaseConfirmedCount = purchaseConfirmedCount;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Tuple tuple;

    public Builder tuple(Tuple tuple) {
      this.tuple = tuple;
      return this;
    }

    public SalesWrapper build(ModelMapper mapper) {
      if (this.tuple == null) {
        return null;
      }

      Sales sales = tuple.get(0, Sales.class);
      SalesDTO dto = mapper.map(sales, SalesDTO.class);
      int orderCount = tuple.get(1, Number.class).intValue();
      int totalPrice = tuple.get(2, Number.class).intValue();
      int confirmedTotalPrice = tuple.get(3, Number.class).intValue();
      int paidOrderCount = tuple.get(4, Number.class).intValue();
      int onDeliveryCount = tuple.get(5, Number.class).intValue();
      int deliveredOrderCount = tuple.get(6, Number.class).intValue();
      int purchaseConfirmedCount = tuple.get(7, Number.class).intValue();

      return new SalesWrapper(dto, orderCount, totalPrice, confirmedTotalPrice,
          paidOrderCount, onDeliveryCount, deliveredOrderCount, purchaseConfirmedCount);
    }
  }
}
