package com.example.lotteon.controller.admin.seller;

import com.example.lotteon.dto.seller.SalesWrapper;
import com.example.lotteon.service.seller.SalesService;
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
@RequestMapping("/admin/sales")
public class AdminSalesController {

  private final SalesService service;

  @GetMapping("/list")
  public String list(@RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      @RequestParam(name = "filter", required = false, defaultValue = "all") String filter,
      @RequestParam(name = "sort", required = false, defaultValue = "asc") String sort,
      Model model) {
    Pageable pageable = PageRequest.of(page - 1, size);
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetails details = (UserDetails) auth.getPrincipal();
    boolean isSeller = details.getAuthorities().stream()
        .anyMatch(g -> g.getAuthority().equals("ROLE_SELLER"));

    Page<SalesWrapper> pages = null;
    String role = null;

    if (isSeller) {
      String sellerId = details.getUsername();
      pages = service.list(filter, sort, sellerId, pageable);
      role = "ROLE_SELLER";
    } else {
      pages = service.list(filter, sort, pageable);
      role = "ROLE_ADMIN";
    }

    model.addAttribute("currentPage", page);
    model.addAttribute("pages", pages);
    model.addAttribute("role", role);

    return "/admin/shop/sales";
  }
}
