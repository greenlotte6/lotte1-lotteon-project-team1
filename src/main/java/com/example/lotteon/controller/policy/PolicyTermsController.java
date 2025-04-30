package com.example.lotteon.controller.policy;

import com.example.lotteon.dto.TermsDTO;
import com.example.lotteon.service.TermsService;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PolicyTermsController {

  private final TermsService termsService;

  // 구매 약관
  @GetMapping("/policy/buyer")
  public String buyer(Model model) {
    List<TermsDTO> termsDTO = termsService.terms();
    model.addAttribute("terms", termsDTO);
    return "/common/policy/buyer";
  }

  // 판매 약관
  @GetMapping("/policy/seller")
  public String seller(Model model) {
    List<TermsDTO> termsDTO = termsService.terms();
    model.addAttribute("terms", termsDTO);
    return "/common/policy/seller";
  }


  // 전자금융거래 약관
  @GetMapping("/policy/finance")
  public String finance(Model model) {
    List<TermsDTO> termsDTO = termsService.terms();
    model.addAttribute("terms", termsDTO);
    return "/common/policy/finance";
  }

  // 위치정보 약관
  @GetMapping("/policy/location")
  public String location(Model model) {
    List<TermsDTO> termsDTO = termsService.terms();
    model.addAttribute("terms", termsDTO);
    return "/common/policy/location";
  }

  // 개인정보처리 약관
  @GetMapping("/policy/privacy")
  public String privacy(Model model) {
    List<TermsDTO> termsDTO = termsService.terms();
    model.addAttribute("terms", termsDTO);
    return "/common/policy/privacy";
  }




}
