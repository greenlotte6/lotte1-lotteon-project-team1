package com.example.lotteon.interceptor;

import com.example.lotteon.entity.admin.config.ConfigDocument;
import com.example.lotteon.service.admin.AdminConfigService;
import com.example.lotteon.service.admin.CacheService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 작성자: 이현민(id3ntity99) <br> 관리자가 설정한 기본설정을 모든 페이지에 적용하기 위한 인터셉터
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ConfigApplicationInterceptor implements HandlerInterceptor {

  private final CacheService service;
  private final AdminConfigService adminConfigService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.info("Intercepted incoming request to {}", request.getRequestURI());
    ConfigDocument cachedConfig = service.getCachedConfig();

    if (cachedConfig == null) {
      cachedConfig = adminConfigService.getConfig();
    }
    request.setAttribute("config", cachedConfig);

    return true;
  }
}
