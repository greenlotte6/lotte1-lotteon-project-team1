package com.example.lotteon.config;

import com.example.lotteon.interceptor.AppVersionSetter;
import com.example.lotteon.interceptor.ConfigApplicationInterceptor;
import com.example.lotteon.redis.repository.GlobalHitRepository;
import com.example.lotteon.service.admin.BasicConfigService;
import com.example.lotteon.service.admin.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

  @Value("${lotteon.upload.path}")
  private String uploadPath;
  private final AppVersionSetter appVersionSetter;
  private final GlobalHitRepository globalHitRepository;
  private final CacheService cacheService;
  private final BasicConfigService basicConfigService;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(appVersionSetter)
        .excludePathPatterns("/style/**", "/js/**", "/images/**");
    registry.addInterceptor(new ConfigApplicationInterceptor(cacheService, basicConfigService))
        .excludePathPatterns("/upload/**", "/error", "/static/**", "/style/**", "/js/**",
            "/images/**", "/proDetail-img/**", "/search/**");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/upload/**")
        .addResourceLocations("file:" + uploadPath + "/");
    registry.addResourceHandler("/upload/product/**")
        .addResourceLocations("file:" + uploadPath + "/product/");
    registry.addResourceHandler("/upload/banner/**")
        .addResourceLocations("file:" + uploadPath + "/banner/");
  }
}
