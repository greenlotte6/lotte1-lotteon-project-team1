package com.example.lotteon.controller.admin.order;

import com.example.lotteon.dto.order.OrderWrapper;
import com.example.lotteon.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/order")
public class OrderManagementController {

  private final OrderService orderService;

  @GetMapping("/list")
  public String list(
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      Model model) {
    Pageable pageable = PageRequest.of(page - 1, size);
    Page<OrderWrapper> pages = orderService.listOrders(pageable);

    model.addAttribute("currentPage", page);
    model.addAttribute("pages", pages);

    return "/admin/order/order";
  }

  @GetMapping("/search")
  public String search(@RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      @RequestParam String filter,
      @RequestParam String keyword,
      Model model
  ) {
    Pageable pageable = PageRequest.of(page - 1, size);
    Page<OrderWrapper> pages = null;
    switch (filter) {
      case "orderNumber": {
        pages = orderService.searchByOrderNumber(pageable, keyword);
        break;
      }
      case "memberId": {
        pages = orderService.searchByMemberId(pageable, keyword);
        break;
      }
      case "memberName": {
        pages = orderService.searchByMemberName(pageable, keyword);
        break;
      }
    }

    model.addAttribute("currentPage", page);
    model.addAttribute("pages", pages);

    return "/admin/order/order";
  }
}
