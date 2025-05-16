package com.example.lotteon.service.admin.statistics;

import com.example.lotteon.dto.admin.StatisticDTO;
import com.example.lotteon.repository.UserRepository;
import com.example.lotteon.repository.cs.QnaRepository;
import com.example.lotteon.repository.order.OrderRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

  private final UserRepository userRepo;
  private final OrderRepository orderRepo;
  private final QnaRepository qnaRepo;

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

  public StatisticDTO getAdminStat(int orderStatus) {
    //TODO Controller will use this method
    long orderCount = countAllOrders();
    long orderCountByStatus = countAllOrdersByStatus(orderStatus);
    long totalSales = getTotalSales();
    long newUsersCount = countNewUsers();
    long hitCount = countHit();
    long qnaCount = countAllQna();
    return StatisticDTO.builder()
        .build();
  }
}
