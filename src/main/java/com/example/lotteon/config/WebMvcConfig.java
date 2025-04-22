package com.example.lotteon.config;

import com.example.lotteon.redis.repository.GlobalHitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

  private final GlobalHitRepository globalHitRepository;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
  }
}
