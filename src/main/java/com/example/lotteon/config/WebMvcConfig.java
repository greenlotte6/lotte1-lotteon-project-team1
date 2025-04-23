package com.example.lotteon.config;

import com.example.lotteon.interceptor.ConfigApplicationInterceptor;
import com.example.lotteon.redis.repository.GlobalHitRepository;
import com.example.lotteon.service.admin.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

  private final GlobalHitRepository globalHitRepository;
  private final CacheService adminConfigService;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new ConfigApplicationInterceptor(adminConfigService))
        .excludePathPatterns("/style/**", "/js/**", "/images/**");
  }
}
