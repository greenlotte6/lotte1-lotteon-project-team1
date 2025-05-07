package com.example.lotteon.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    MyUserDetails loginUser = (MyUserDetails) authentication.getPrincipal(); // ✅ 수정: User ➔ MyUserDetails
    HttpSession session = request.getSession();
    session.setAttribute("loginUserId", loginUser.getUsername()); // ✅ 여기서 username 가져옴

    log.info("로그인 성공 - 세션에 loginUserId 저장: {}", loginUser.getUsername());

    // 로그인 성공 후 메인페이지로 이동
    response.sendRedirect("/");
  }
}
