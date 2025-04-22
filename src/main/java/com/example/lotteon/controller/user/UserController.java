package com.example.lotteon.controller.user;

import com.example.lotteon.dto.TermsDTO;
import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.dto.user.UserCompositeKeyDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.service.TermsService;
import com.example.lotteon.service.user.MemberService;
import com.example.lotteon.service.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final TermsService termsService;
  private final UserService userService;
  private final MemberService memberService;

  @GetMapping("/general")
  public String general() {
    return "/user/general";
  }

  @PostMapping("/general")
  public String general(UserDTO userDTO, MemberDTO memberDTO) {
    UserCompositeKeyDTO compositeKeyDTO = UserCompositeKeyDTO.builder()
        .user(userDTO)
        .build();

    memberDTO.setUserCompositeKey(compositeKeyDTO);

    // 서비스 호출
    userService.userRegister(userDTO);
    memberService.memberRegister(memberDTO);

    // 리다이렉트
    return "redirect:/user/general";
  }

  @GetMapping("/login")
  public String login() {
    return "/user/login";
  }

  @GetMapping("/seller")
  public String seller() {
    return "/user/seller";
  }

  @GetMapping("/sort")
  public String sort() {
    return "/user/sort";
  }

  @GetMapping("/terms")
  public String terms(Model model) {
    List<TermsDTO> termsDTO = termsService.terms();
    model.addAttribute("terms", termsDTO);
    return "/user/terms";
  }

  @GetMapping("/tax")
  public String tax() {
    return "/user/tax";
  }
}
