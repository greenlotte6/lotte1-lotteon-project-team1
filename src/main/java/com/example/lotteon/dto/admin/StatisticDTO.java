package com.example.lotteon.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDTO {

  long paidCount;
  long paymentWaitingCount;
  long prepareDeliveryCount;
  long cancelRequestedCount;
  long exchangeRequestedCount;
  long refundRequestedCount;
  long orderCount;
  long totalSales;
  long newUserCount;
  long hitCount;
  long qnaCount;
}
