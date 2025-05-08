package com.example.lotteon.controller.admin.order;

import com.example.lotteon.dto.order.DeliveryDTO;
import com.example.lotteon.dto.order.DeliveryWrapper;
import com.example.lotteon.dto.order.OrderStatusDTO;
import com.example.lotteon.service.delivery.DeliveryService;
import com.example.lotteon.service.order.OrderService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/delivery")
public class DeliveryController {

  private final DeliveryService deliService;
  private final OrderService orderService;

  private UserDetails getUserDetails() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return (UserDetails) auth.getPrincipal();
  }

  @GetMapping("/list")
  public String list(@RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      Model model) {
    Pageable pageable = PageRequest.of(page - 1, size);
    UserDetails details = getUserDetails();

    boolean isSeller = details.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals("ROLE_SELLER"));
    boolean isAdmin = details.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    String role = null;
    Page<DeliveryWrapper> pages = null;

    if (isSeller) {
      role = "ROLE_SELLER";
      String currentSellerId = details.getUsername();
      pages = deliService.list(currentSellerId, pageable);
    } else if (isAdmin) {
      role = "ROLE_ADMIN";
      pages = deliService.list(pageable);
    }

    model.addAttribute("role", role);
    model.addAttribute("currentPage", page);
    model.addAttribute("pages", pages);
    return "/admin/order/delivery";
  }

  @GetMapping("/search")
  public String search(@RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      @RequestParam("filter") String filter,
      @RequestParam("keyword") String keyword,
      Model model) {
    Pageable pageable = PageRequest.of(page - 1, size);
    UserDetails details = getUserDetails();

    boolean isSeller = details.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals("ROLE_SELLER"));
    boolean isAdmin = details.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    String role = null;
    Page<DeliveryWrapper> pages = null;

    switch (filter) {
      case "deliveryNumber": {
        if (isSeller) {
          String currentSellerId = details.getUsername();
          role = "ROLE_SELLER";
          pages = deliService.searchByDeliveryNumber(currentSellerId, keyword, pageable);
        } else if (isAdmin) {
          role = "ROLE_ADMIN";
          pages = deliService.searchByDeliveryNumber(keyword, pageable);
        }
        break;
      }
      case "orderNumber": {
        if (isSeller) {
          String currentSellerId = details.getUsername();
          role = "ROLE_SELLER";
          pages = deliService.searchByOrderNumber(currentSellerId, keyword, pageable);
        } else if (isAdmin) {
          role = "ROLE_ADMIN";
          pages = deliService.searchByOrderNumber(keyword, pageable);
        }
        break;
      }
      case "recipientName": {
        if (isSeller) {
          String currentSellerId = details.getUsername();
          role = "ROLE_SELLER";
          pages = deliService.searchByRecipientName(currentSellerId, keyword, pageable);
        } else if (isAdmin) {
          role = "ROLE_ADMIN";
          pages = deliService.searchByRecipientName(keyword, pageable);
        }
        break;
      }
    }

    model.addAttribute("role", role);
    model.addAttribute("currentPage", page);
    model.addAttribute("pages", pages);
    return "/admin/order/delivery";
  }

  @PostMapping("register")
  public String register(DeliveryDTO delivery) {
    String orderNumber = delivery.getOrder().getOrderNumber();
    OrderStatusDTO status = OrderStatusDTO.builder()
        .id(3)
        .name(OrderStatusDTO.STATUS_PREPARE_DELIVERY)
        .build();
    LocalDateTime localCurrentDate = LocalDateTime.now();
    Date currentDate = Date.from(localCurrentDate.atZone(ZoneId.systemDefault()).toInstant());
    delivery.setReceiptDate(currentDate);
    orderService.updateStatusByOrderNumber(orderNumber, status);
    deliService.save(delivery);
    return "redirect:/admin/order/list";
  }
}
