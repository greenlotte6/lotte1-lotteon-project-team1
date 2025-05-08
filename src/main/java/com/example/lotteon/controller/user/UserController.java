package com.example.lotteon.controller.user;

import com.example.lotteon.dto.TermsDTO;
import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.exception.EntityAlreadyExistsException;
import com.example.lotteon.service.TermsService;
import com.example.lotteon.service.user.MemberService;
import com.example.lotteon.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

  private final TermsService termsService;
  private final UserService userService;
  private final MemberService memberService;

  @GetMapping("/general")
  public String general() {
    return "/user/general";
  }

  @PostMapping("/general")
  public String general(MemberDTO memberDTO, String passwordConfirm, HttpServletResponse response) {
    // 서비스 호출
    try {
      memberDTO.setStatus("normal");
      memberDTO.setLevel("family");
      UserDTO userDTO = memberDTO.getMemberId().getUser();
      userService.register(userDTO, UserDTO.ROLE_MEMBER);
      memberService.memberRegister(memberDTO);
    } catch (EntityAlreadyExistsException e) { // 같은 id의 유저가 이미 존재할 경우
      response.setStatus(HttpServletResponse.SC_CONFLICT); // 409 에러 응답 전송
      return null;
    }

    // 리다이렉트
    return "redirect:/login";
  }

  @GetMapping("/login")
  public String login() {

    return "/user/login";
  }

  @GetMapping("/seller")
  public String seller() {
    return "/user/seller";
  }

  @GetMapping("/user/sort")
  public String sort() {
    return "/user/sort";
  }

  @GetMapping("/user/terms")
  public String terms(Model model) {
    List<TermsDTO> termsDTO = termsService.terms();
    model.addAttribute("terms", termsDTO);
    return "/user/terms";
  }

  @GetMapping("/tax")
  public String tax() {
    return "/user/tax";
  }

  // 유효성 검사
  @GetMapping("check/{type}/{value}")
  public ResponseEntity<Map<String, Long>> user(@PathVariable("type") String type,
      @PathVariable("value") String value) {
    log.info("type : " + type + ", value : " + value);
    // 서비스 호출
    long count = userService.checkUser(type, value);

    // JSON 생성
    Map<String, Long> resultMap = new HashMap<>();
    resultMap.put("count", count);

    // JSON 반환
    return ResponseEntity.ok().body(resultMap);
  }

  // JSON 단일 문자열값이 직접 String으로 매핑되지 않기 때문에 JSON과 호환되는 Map 타입으로 JSON 수신
  @PostMapping("/email/auth")
  public ResponseEntity<Boolean> emailAuth(@RequestBody Map<String, String> map,
      HttpSession session) {
    String code = map.get("code");
    log.info("code : " + code);

    String sessAuthCode = (String) session.getAttribute("code");
    log.info("sessAuthCode : {}", sessAuthCode);

    if (code.equals(sessAuthCode)) {
      return ResponseEntity.ok().body(true);
    }

    return ResponseEntity.ok().body(false);
  }

}
