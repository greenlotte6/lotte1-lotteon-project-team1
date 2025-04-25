package com.example.lotteon.controller.admin.seller;

import com.example.lotteon.dto.seller.SellerDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.seller.Seller;
import com.example.lotteon.service.seller.SellerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/admin/seller")
@RequiredArgsConstructor
public class AdminSellerController {

  private final SellerService seller;

  @GetMapping("/list")
  public String list(Model model) {
    List<Seller> sellers = seller.getAll();
    model.addAttribute("sellers", sellers);
    return "/admin/shop/shop";
  }

  @PostMapping("/register")
  public String register(SellerDTO sellerDTO,
      UserDTO userDTO,
      @RequestParam String passwordConfirm) {
    if (userDTO.getPassword().equals(passwordConfirm)) {
      sellerDTO.setUser(userDTO);
    } else {
      //TODO: Send error page
    }
    log.info("Register seller: " + sellerDTO);
    return "redirect:/admin/seller/list";
  }
}
