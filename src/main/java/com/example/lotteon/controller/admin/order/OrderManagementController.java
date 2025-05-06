package com.example.lotteon.controller.admin.order;

import com.example.lotteon.dto.order.OrderWrapper;
import com.example.lotteon.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
  private final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

  @GetMapping("/list")
  public String list(
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      Model model) {
    Pageable pageable = PageRequest.of(page - 1, size);

    UserDetails details = (UserDetails) auth.getPrincipal();

    Page<OrderWrapper> pages = orderService.getAllOrdersBySellerId(details.getUsername(), pageable);

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
    UserDetails details = (UserDetails) auth.getPrincipal();
    boolean isSeller = details.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals("ROLE_SELLER"));
    Page<OrderWrapper> pages = null;
    switch (filter) {
      case "orderNumber": {
        if (isSeller) {
          pages = orderService.searchByOrderNumber(details.getUsername(), keyword, pageable);
        } else {
          //TODO: Get all orders regardless of seller id
        }
        break;
      }
      case "memberId": {
        if (isSeller) {
          pages = orderService.searchByMemberId(details.getUsername(), keyword, pageable);
        } else {
          //TODO: Get all orders regardless of seller id
        }
        break;
      }
      case "memberName": {
        if (isSeller) {
          pages = orderService.searchByMemberName(details.getUsername(), keyword, pageable);
        } else {
          //TODO: Get all orders regardless of seller id
        }
        break;
      }
      default:
        break;
    }

    model.addAttribute("currentPage", page);
    model.addAttribute("pages", pages);

    return "/admin/order/order";
  }
}
