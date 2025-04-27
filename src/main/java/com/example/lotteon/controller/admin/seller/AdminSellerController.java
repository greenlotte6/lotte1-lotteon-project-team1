package com.example.lotteon.controller.admin.seller;

import com.example.lotteon.dto.seller.SellerDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.seller.Seller;
import com.example.lotteon.exception.EntityAlreadyExistsException;
import com.example.lotteon.service.seller.SellerService;
import com.example.lotteon.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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

  private final UserService userService;
  private final SellerService service;
  private final PasswordEncoder encoder;

  @GetMapping("/list")
  public String list(Model model) {
    List<Seller> sellers = service.getAll();
    model.addAttribute("sellers", sellers);
    return "/admin/shop/shop";
  }

  /*
  SellerDTO는 sellerId:SellerIdDTO 속성을 가지고 있고, sellerId는 다시 businessNumber:String, user:UserDTO를 가지고 있다.
  그 이유는 Seller entity가 복합키를 가지기 때문이다.
  이런 경우에는 form 데이터 field명이 다음과 같아야 한다.
  sellerId.businessNumber
  sellerId.user.id
  sellerId.user.contact
  왜냐하면, Spring은 POJO(DTO)의 속성 이름을 참조해 form data를 mapping하기 때문이다.
   */
  @PostMapping("/register")
  public String register(
      SellerDTO seller,
      @RequestParam String passwordConfirm,
      HttpServletResponse response) {
    if (seller.getSellerId().getUser().getPassword().equals(passwordConfirm)) {
      UserDTO user = seller.getSellerId().getUser();
      try {
        userService.register(user, UserDTO.ROLE_SELLER);
        service.save(seller);
      } catch (EntityAlreadyExistsException e) {
        log.warn(e.getMessage());
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        return null;
      }
    }
    return "redirect:/admin/seller/list";
  }
}
