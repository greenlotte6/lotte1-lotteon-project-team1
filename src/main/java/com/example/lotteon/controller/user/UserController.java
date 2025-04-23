package com.example.lotteon.controller.user;

import com.example.lotteon.dto.TermsDTO;
import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.dto.user.UserCompositeKeyDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.service.TermsService;
import com.example.lotteon.service.user.MemberService;
import com.example.lotteon.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
  public String general(UserDTO userDTO, MemberDTO memberDTO) {
    UserCompositeKeyDTO compositeKeyDTO = UserCompositeKeyDTO.builder()
        .user(userDTO)
        .build();

    memberDTO.setUserCompositeKey(compositeKeyDTO);

    // 서비스 호출
    userService.userRegister(userDTO);
    memberService.memberRegister(memberDTO);

    // 리다이렉트
    return "redirect:/user/login";
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

  // 유효성 검사
  //@GetMapping("/{type}/{value}")
  //public ResponseEntity user(@PathVariable("type") String type, @PathVariable("value") String value) {
  //  log.info("type : " + type + ", value : " + value);

  //  // 서비스 호출
  //  long count = userService.checkUser(type, value);

  //  // JSON 생성
  //  Map<String, Long> resultMap = new HashMap<>();
  //  resultMap.put("count", count);

  //  // JSON 반환
  //  return ResponseEntity.ok().body(resultMap);
  //}

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
