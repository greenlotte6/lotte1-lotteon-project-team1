package com.example.lotteon.service.admin.statistics;

import com.example.lotteon.dto.admin.StatisticDTO;
import com.example.lotteon.repository.order.OrderRepository;
import com.example.lotteon.repository.seller.SalesRepository;
import com.example.lotteon.service.cs.QnaService;
import com.example.lotteon.service.user.UserService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

  private final OrderRepository orderRepo;
  private final UserService userService;
  private final QnaService qnaService;
  private final SalesRepository salesRepo;

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
    return orderRepo.findTotalSales(sellerId, LocalDate.of(2025, 5, 13));
  }

  public long countNewUsers() {
    return 0;
  }

  public long countHit() {
    return 0;
  }

  public long countAllQna() {
    return 0;
  }

  public StatisticDTO getStatistics() {
    //TODO Controller will use this method
    return new StatisticDTO();
  }
}
