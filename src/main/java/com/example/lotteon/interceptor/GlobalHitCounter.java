package com.example.lotteon.interceptor;

import com.example.lotteon.redis.entity.GlobalHit;
import com.example.lotteon.redis.repository.GlobalHitRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class GlobalHitCounter implements HandlerInterceptor {

  private final GlobalHitRepository repo;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    boolean counterExists = repo.existsById("global:counter");

    if (!counterExists) {
      GlobalHit hit = new GlobalHit();
      //TODO: Set ttl for counter
    }

    Optional<GlobalHit> optional = repo.findById("global:counter");
    if (optional.isPresent()) {
      GlobalHit hit = optional.get();
      int currentHit = hit.getHit();
      int countedHit = currentHit++;
      hit.setHit(countedHit);
      repo.save(hit);
    } else {
      return false;
    }

    return true;
  }
}
