package com.example.lotteon.config;

import com.example.lotteon.interceptor.GlobalHitCounter;
import com.example.lotteon.redis.repository.GlobalHitRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Bean
  public GlobalHitCounter globalHitCounter(GlobalHitRepository repository) {
    return new GlobalHitCounter(repository);
  }

  @Bean
  public Gson gson() {
    return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
  }
}
