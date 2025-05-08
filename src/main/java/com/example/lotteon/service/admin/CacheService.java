package com.example.lotteon.service.admin;

import com.example.lotteon.entity.admin.config.ConfigDocument;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {

  private final RedisTemplate<String, String> template;
  private final Gson gson;

  public ConfigDocument getCachedConfig() {
    ValueOperations<String, String> ops = template.opsForValue();
    String cachedData = ops.get("admin::basic_config");
    if (cachedData != null) {
      return gson.fromJson(cachedData, ConfigDocument.class);
    }
    return null;
  }

  public void cache(String serializedConfig) {
    ValueOperations<String, String> ops = template.opsForValue();
    log.info("Cached config not found. Caching config...");
    ops.set("admin::basic_config", serializedConfig);
    log.info("Caching complete.\n {}", serializedConfig);
  }

  public void invalidateCache() {
    template.delete("admin::basic_config");
    log.info("Cache invalidated");
  }
}
