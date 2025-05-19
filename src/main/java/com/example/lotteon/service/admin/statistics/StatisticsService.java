package com.example.lotteon.service.admin.statistics;

import com.example.lotteon.dto.admin.StatisticDTO;
import com.example.lotteon.dto.order.OrderStatusDTO;
import com.example.lotteon.repository.jpa.UserRepository;
import com.example.lotteon.repository.jpa.cs.QnaRepository;
import com.example.lotteon.repository.jpa.order.OrderRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.querydsl.core.Tuple;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

  private final UserRepository userRepo;
  private final OrderRepository orderRepo;
  private final QnaRepository qnaRepo;
  private final ModelMapper mapper;
  private final Gson gson;

  // 주문건수
  public long countAllOrders() {
    LocalDate today = LocalDate.now();
    return orderRepo.count(today);
  }

  public long countAllOrders(String sellerId) {
    LocalDate today = LocalDate.now();
    return orderRepo.countBySellerId(sellerId, today);
  }

  public long countAllOrdersByStatus(int status) {
    LocalDate today = LocalDate.now();
    return orderRepo.countByStatus(status, today);
  }

  public long countAllOrdersByStatus(int status, String sellerId) {
    LocalDate today = LocalDate.now();
    return orderRepo.countByStatus(sellerId, status, today);
  }

  public long getTotalSales() {
    LocalDate today = LocalDate.now();
    return orderRepo.findTotalSales(today);
  }

  public long getTotalSales(String sellerId) {
    LocalDate today = LocalDate.now();
    return orderRepo.findTotalSales(sellerId, today);
  }

  public long countNewUsers() {
    LocalDate today = LocalDate.now();
    return userRepo.countNewUsers(today);
  }

  public long countHit() {
    return 0;
  }

  public long countAllQna() {
    LocalDate today = LocalDate.now();
    return qnaRepo.countAll(today);
  }

  public String getBarData(LocalDate from, LocalDate to, int... statuses) {
    List<Tuple> tuples = orderRepo.countByStatusBetween(from, to, statuses);
    return null;
  }

  public String getBarData(String sellerId, LocalDate from, LocalDate to) {
    long confirmedCount = orderRepo.countByStatusBetween(
        OrderStatusDTO.STATUS_PURCHASE_CONFIRMED_ID, sellerId, from, to);
    long canceledCount = orderRepo.countByStatusBetween(OrderStatusDTO.STATUS_CANCELLED_ID,
        sellerId, from,
        to);
    long onDeliveryCount = orderRepo.countByStatusBetween(OrderStatusDTO.STATUS_ON_DELIVERY_ID,
        sellerId, from, to);
    JsonObject json = new JsonObject();
    json.addProperty("confirmed", confirmedCount);
    json.addProperty("canceled", canceledCount);
    json.addProperty("on_delivery", onDeliveryCount);
    return gson.toJson(json);
  }

  public String getPieData(LocalDate from, LocalDate to) {
    return null;
  }

  public StatisticDTO getSellerStat(String sellerId) {
    long orderCount = countAllOrders(sellerId);
    long totalSales = getTotalSales(sellerId);
    return StatisticDTO.builder()
        .paidCount(countAllOrdersByStatus(OrderStatusDTO.STATUS_PAID_ID, sellerId))
        .paymentWaitingCount(
            countAllOrdersByStatus(OrderStatusDTO.STATUS_PAYMENT_WAITING_ID, sellerId))
        .cancelRequestedCount(
            countAllOrdersByStatus(OrderStatusDTO.STATUS_CANCEL_REQUESTED_ID, sellerId))
        .exchangeRequestedCount(
            countAllOrdersByStatus(OrderStatusDTO.STATUS_EXCHANGE_REQUESTED_ID, sellerId))
        .prepareDeliveryCount(
            countAllOrdersByStatus(OrderStatusDTO.STATUS_PREPARE_DELIVERY_ID, sellerId))
        .refundRequestedCount(
            countAllOrdersByStatus(OrderStatusDTO.STATUS_REFUND_REQUESTED_ID, sellerId))
        .totalSales(totalSales)
        .orderCount(orderCount)
        .build();
  }

  public StatisticDTO getAdminStat() {
    long orderCount = countAllOrders();
    long totalSales = getTotalSales();
    long newUsersCount = countNewUsers();
    long hitCount = countHit();
    long qnaCount = countAllQna();
    return StatisticDTO.builder()
        .paidCount(countAllOrdersByStatus(OrderStatusDTO.STATUS_PAID_ID))
        .paymentWaitingCount(countAllOrdersByStatus(OrderStatusDTO.STATUS_PAYMENT_WAITING_ID))
        .cancelRequestedCount(countAllOrdersByStatus(OrderStatusDTO.STATUS_CANCEL_REQUESTED_ID))
        .exchangeRequestedCount(countAllOrdersByStatus(OrderStatusDTO.STATUS_EXCHANGE_REQUESTED_ID))
        .prepareDeliveryCount(countAllOrdersByStatus(OrderStatusDTO.STATUS_PREPARE_DELIVERY_ID))
        .refundRequestedCount(countAllOrdersByStatus(OrderStatusDTO.STATUS_REFUND_REQUESTED_ID))
        .totalSales(totalSales)
        .newUserCount(newUsersCount)
        .hitCount(hitCount)
        .qnaCount(qnaCount)
        .orderCount(orderCount)
        .build();
  }
}
