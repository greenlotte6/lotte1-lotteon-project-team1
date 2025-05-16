package com.example.lotteon.controller.user;

import com.example.lotteon.dto.TermsDTO;
import com.example.lotteon.dto.seller.SellerDTO;
import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.admin.banner.BannerDocument;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.exception.EntityAlreadyExistsException;
import com.example.lotteon.service.TermsService;
import com.example.lotteon.service.admin.CacheService;
import com.example.lotteon.service.seller.SellerService;
import com.example.lotteon.service.user.MemberService;
import com.example.lotteon.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

  private final TermsService termsService;
  private final UserService userService;
  private final MemberService memberService;
  private final SellerService sellerService;
  private final CacheService cacheService;

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

  @GetMapping("/genseller")
  public String seller() {
    return "/user/genseller";
  }

  @PostMapping("/genseller")
  public String genseller(SellerDTO sellerDTO, String passwordConfirm,
      HttpServletResponse response) {
    try {
      UserDTO userDTO = sellerDTO.getSellerId().getUser();
      userDTO.setRole(UserDTO.ROLE_SELLER);
      userService.register(userDTO, UserDTO.ROLE_SELLER);
      sellerDTO.setStatus("ready");
      sellerService.save(sellerDTO);
    } catch (EntityAlreadyExistsException e) {
      response.setStatus(HttpServletResponse.SC_CONFLICT);
      return null;
    }
    return "redirect:/login";
  }

  @GetMapping("/login")
  public String login(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated()) {//유저가 이미 로그인한 경우
      //TODO: Handle this
    }
    List<BannerDocument> banners = cacheService.getCachedBanner("LOGIN");
    if (banners != null) {
      int randomIndex = (int) (Math.random() * banners.size());
      BannerDocument randomBanner = banners.get(randomIndex);
      model.addAttribute("banner", randomBanner);
    }
    return "/user/login";
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

  @GetMapping("/user/terms2")
  public String terms2(Model model) {
    List<TermsDTO> termsDTO = termsService.terms();
    model.addAttribute("terms", termsDTO);
    return "/user/terms2";
  }

  // 유효성 검사
  @GetMapping("check/{type}/{value}")
  public ResponseEntity<Map<String, Long>> user(@PathVariable("type") String type,
      @PathVariable("value") String value) {
    log.info("type : " + type + ", value : " + value);

    long count = userService.checkUser(type, value);

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
    String email = map.get("email");
    String type = map.get("type"); // "findid", "findpw", null

    // 🔥 type에 따라 저장된 세션 키 이름 설정
    String sessionKey;
    if ("findpw".equals(type)) {
      sessionKey = "pw_code_" + email;
    } else {
      // 기본값은 아이디 찾기용 (type이 null이거나 "findid"일 경우)
      sessionKey = "email_code_" + email;
    }

    String sessAuthCode = (String) session.getAttribute(sessionKey);

    log.info("입력한 코드: {}", code);
    log.info("세션 저장된 코드 [{}]: {}", sessionKey, sessAuthCode);

    return ResponseEntity.ok(code.equals(sessAuthCode));
  }


  @GetMapping("/user/findid")
  public String findid() {
    return "/user/findid";
  }

  @PostMapping("/user/findid")
  @ResponseBody
  public ResponseEntity<String> findId(@RequestBody Map<String, String> map, HttpSession session) {
    String name = map.get("name");
    String email = map.get("email");
    String code = map.get("code");

    // 세션에 저장된 인증코드 가져오기
    String sessAuthCode = (String) session.getAttribute("email_code_" + email);
    log.info("입력한 코드: {}, 세션 코드: {}", code, sessAuthCode);

    if (sessAuthCode == null || !sessAuthCode.equals(code)) {
      return ResponseEntity.ok("인증코드가 일치하지 않습니다.");
    }

    // 이름과 이메일로 사용자 아이디 찾기
    return userService.findUserId(name, email)
        .map(id -> ResponseEntity.ok("당신의 아이디는 '" + id + "' 입니다."))
        .orElse(ResponseEntity.ok("일치하는 회원 정보가 없습니다."));
  }

  @PostMapping("/user/sendCode")
  @ResponseBody
  public ResponseEntity<String> sendCode(@RequestBody Map<String, String> map,
      HttpSession session) {
    String email = map.get("email");
    String name = map.get("name");

    // 이름+이메일 매칭 여부 검사
    boolean exists = userService.existsByNameAndEmail(name, email);
    if (!exists) {
      return ResponseEntity.ok("NO_USER");
    }

    String code = userService.sendEmailCode(email);
    session.setAttribute("email_code_" + email, code);
    log.info("이메일 코드 저장: [{}] {}", email, code);

    return ResponseEntity.ok("SENT");
  }


  @GetMapping("/user/findpassword")
  public String findpassword() {
    return "/user/findpassword";
  }

  @PostMapping("/user/findpassword")
  @ResponseBody
  public ResponseEntity<String> findPassword(@RequestBody Map<String, String> map) {
    String id = map.get("id");
    String email = map.get("email");

    Optional<User> optUser = userService.findByIdAndEmail(id, email);
    if (optUser.isEmpty()) {
      return ResponseEntity.ok("일치하는 회원 정보가 없습니다.");
    }

    String tempPassword = userService.sendTempPassword(optUser.get());
    return ResponseEntity.ok("임시 비밀번호가 이메일로 전송되었습니다.");
  }

  @PostMapping("/user/sendPwCode")
  @ResponseBody
  public ResponseEntity<String> sendPwCode(@RequestBody Map<String, String> map,
      HttpSession session) {
    String id = map.get("id");
    String email = map.get("email");

    Optional<User> optUser = userService.findByIdAndEmail(id, email);
    if (optUser.isEmpty()) {
      return ResponseEntity.ok("NO_USER");
    }

    String code = userService.sendEmailCode(email);
    session.setAttribute("pw_code_" + email, code); // 비밀번호 찾기용 코드 저장
    log.info("비밀번호찾기용 인증코드 저장: [{}] {}", email, code);

    return ResponseEntity.ok("SENT");
  }

  @PostMapping("/user/sendJoinCode")
  @ResponseBody
  public ResponseEntity<String> sendJoinCode(@RequestBody Map<String, String> map,
      HttpSession session) {
    String email = map.get("email");

    String code = userService.sendEmailCode(email);
    session.setAttribute("email_code_" + email, code);
    log.info("회원가입용 인증코드 저장: [{}] {}", email, code);

    return ResponseEntity.ok("SENT");
  }
}
