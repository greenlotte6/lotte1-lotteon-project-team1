package com.example.lotteon.controller.admin;

import com.example.lotteon.service.order.OrderService;
import com.example.lotteon.service.user.MemberService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminMainController {

  private final MemberService memberService;
  private final OrderService orderService;

  @GetMapping(value = {"", "/", "/index"})
  public String index(Model model) {
    log.info("request received");
    LocalDate today = LocalDate.now();
    LocalDate yesterday = LocalDate.now().minusDays(1);

    long todayNewMembers = memberService.countNewMembersOf(today);
    long yesterdayNewMembers = memberService.countNewMembersOf(yesterday);
    model.addAttribute("todayNewMembers", todayNewMembers);
    model.addAttribute("yesterdayNewMembers", yesterdayNewMembers);

    long todayTotalPrice = orderService.calculatePriceOfValidOrdersAt(today);
    long yesterdayTotalPrice = orderService.calculatePriceOfValidOrdersAt(yesterday);
    model.addAttribute("todayTotalPrice", todayTotalPrice);
    model.addAttribute("yesterdayTotalPrice", yesterdayTotalPrice);

    long paymentWaitingOrders = orderService.countPaymentWaitingOrdersAt(today);
    long deliveryReadyOrders = orderService.countDeliveryReadyOrdersAt(today);
    long cancelRequestedOrders = orderService.countCancelRequestedOrdersAt(today);
    long exchangeRequestedOrders = orderService.countExchangeRequestedOrdersAt(today);
    long refundRequestedOrders = orderService.countRefundRequestedOrdersAt(today);
    model.addAttribute("paymentWaitingOrders", paymentWaitingOrders);
    model.addAttribute("deliveryReadyOrders", deliveryReadyOrders);
    model.addAttribute("cancelRequestedOrders", cancelRequestedOrders);
    model.addAttribute("exchangeRequestedOrders", exchangeRequestedOrders);
    model.addAttribute("refundRequestedOrders", refundRequestedOrders);

    long todayValidOrders = orderService.countValidOrdersAt(today);
    long yesterdayValidOrders = orderService.countValidOrdersAt(yesterday);
    model.addAttribute("todayValidOrders", todayValidOrders);
    model.addAttribute("yesterdayValidOrders", yesterdayValidOrders);
    return "/admin/index";
  }
}
