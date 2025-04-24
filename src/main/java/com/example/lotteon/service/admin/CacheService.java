package com.example.lotteon.service.admin;

import com.example.lotteon.entity.admin.config.ConfigDocument;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {

  private final static String KEY = "admin::basic_config";

  private final RedisTemplate<String, String> template;
  private final Gson gson;

  public ConfigDocument getCachedConfig() {
    ValueOperations<String, String> ops = template.opsForValue();
    String cachedData = ops.get("admin::basic_config");
    if (cachedData != null) {
      log.info("Cached config found. \n{}", cachedData);
      return gson.fromJson(cachedData, ConfigDocument.class);
    }
    return null;
  }

  /**
   * Get cached config if one exists, or perform caching and return cached config
   */
  public ConfigDocument doCache(String serializedConfig) {
    ConfigDocument cachedConfig = getCachedConfig();
    if (cachedConfig == null) { // 캐싱된 기본설정이 없는 경우, 캐싱 진행
      ValueOperations<String, String> ops = template.opsForValue();
      log.info("Cached config not found. Caching config...");
      ops.set("admin::basic_config", serializedConfig);
      log.info("Caching complete.\n {}", serializedConfig);
      return gson.fromJson(serializedConfig, ConfigDocument.class);
    }
    return cachedConfig;
  }

  public void invalidateCache() {
    template.delete("admin::basic_config");
    log.info("Cache invalidated");
  }
}
