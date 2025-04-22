package com.example.lotteon.interceptor;

import com.example.lotteon.redis.entity.GlobalHit;
import com.example.lotteon.redis.repository.GlobalHitRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalHitCounter implements HandlerInterceptor {

  private final GlobalHitRepository repo;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    boolean counterExists = repo.existsById("");

    if (!counterExists) {
      log.info("global:counter seems to be expired. Initiating new counter");
      GlobalHit hit = new GlobalHit();
      LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
      LocalDateTime tomorrowMidNight = tomorrow.with(LocalTime.MIDNIGHT);
      long currentEpoch = System.currentTimeMillis() / 1000;
      long midNightEpoch = tomorrowMidNight.toEpochSecond(ZoneOffset.of("+9"));
      long ttl = midNightEpoch - currentEpoch;
      log.info("Initiating global:counter with ttl of value {}", ttl);
      hit.setHit(0);
      hit.setTtl(ttl);
    }

    log.info("global:counter exists");

    Optional<GlobalHit> optional = repo.findById("global:counter");
    if (optional.isPresent()) {
      GlobalHit hit = optional.get();
      long countedHit = hit.getHit() + 1;
      hit.setHit(countedHit);
      repo.save(hit);
    } else {
      log.warn("Cannot find global:counter");
      return false;
    }

    return HandlerInterceptor.super.preHandle(request, response, handler);
  }
}
