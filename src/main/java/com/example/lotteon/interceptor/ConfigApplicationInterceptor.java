package com.example.lotteon.interceptor;

import com.example.lotteon.entity.admin.config.ConfigDocument;
import com.example.lotteon.service.admin.BasicConfigService;
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
  private final BasicConfigService basicConfigService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (!request.getMethod().equals("GET")) {
      return true;
    }

    log.info("Intercepted incoming request to {} from {}", request.getRequestURI(),
        request.getRemoteAddr());
    ConfigDocument cachedConfig = service.getCachedConfig();

    if (cachedConfig == null) {
      log.info("No cached config found. Retrieving config from database");
      cachedConfig = basicConfigService.getConfig();
    }
    request.setAttribute("config", cachedConfig);

    return true;
  }
}
