package com.example.lotteon.config;

import com.example.lotteon.interceptor.GlobalHitCounter;
import com.example.lotteon.redis.repository.GlobalHitRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Bean
  public GlobalHitCounter globalHitCounter(GlobalHitRepository repository) {
    return new GlobalHitCounter(repository);
  }
}
