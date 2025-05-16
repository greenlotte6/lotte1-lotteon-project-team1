package com.example.lotteon.controller.admin;

import com.example.lotteon.entity.cs.Notice;
import com.example.lotteon.entity.cs.Qna;
import com.example.lotteon.service.admin.statistics.StatisticsService;
import com.example.lotteon.service.cs.CsService;
import com.example.lotteon.service.cs.QnaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminMainController {

  private final CsService csService;
  private final QnaService qnaService;
  private final StatisticsService statService;

  @GetMapping(value = {"", "/", "/index"})
  public String index(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetails details = (UserDetails) auth.getPrincipal();
    boolean isSeller = details.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals("ROLE_SELLER"));

    String role = null;
    long orderCount = 0L;
    long paidCount = 0L;
    long totalSales = 0L;

    if (isSeller) {
      role = "ROLE_SELLER";
      orderCount = statService.countAllOrders(details.getUsername());
      totalSales = statService.getTotalSales(details.getUsername());
      System.out.println(totalSales);

    } else {
      role = "ROLE_ADMIN";
      List<Notice> notices = csService.getNoticeLimit(5);
      List<Qna> qnas = qnaService.getWithLimit(5);
      orderCount = statService.countAllOrders();
      paidCount = statService.countAllOrdersByStatus(2);
      totalSales = statService.getTotalSales();
      long userCount = statService.countNewUsers();

      model.addAttribute("notices", notices);
      model.addAttribute("qnas", qnas);
    }

    model.addAttribute("orderCount", orderCount);
    model.addAttribute("role", role);
    return "/admin/index";
  }
}
