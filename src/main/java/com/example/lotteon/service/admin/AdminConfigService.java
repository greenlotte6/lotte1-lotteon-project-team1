package com.example.lotteon.service.admin;

import com.example.lotteon.entity.admin.config.ConfigDocument;
import com.example.lotteon.entity.admin.config.CorpInfo;
import com.example.lotteon.entity.admin.config.CustomerServiceInfo;
import com.example.lotteon.entity.admin.config.Logo;
import com.example.lotteon.entity.admin.config.Site;
import com.example.lotteon.repository.admin.AdminConfigRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminConfigService {

  private final CacheService cacheService;
  private final RedisTemplate<String, String> template;
  private final AdminConfigRepository repo;
  private final Gson gson;

  //TODO: Cache updated config, or update the cached config using redis
  public ConfigDocument getConfig() {
    ConfigDocument config = repo.find();
    String serializedConfig = gson.toJson(config);
    return cacheService.doCache(serializedConfig);
  }

  public void updateSite(Site config) {
    repo.updateSite(config);
  }

  public void updateLogo(Logo config) {
    repo.updateLogo(config);
  }

  public Logo updateLogoBy(String field, String value) {
    return repo.updateLogoBy(field, value);
  }

  public void updateCorpInfo(CorpInfo config) {
    repo.updateCorpInfo(config);
  }

  public void updateCsInfo(CustomerServiceInfo config) {
    repo.updateCsInfo(config);
  }

  public void updateCopyright(String key, String value) {
    JsonObject json = gson.fromJson(value, JsonObject.class);
    String fieldValue = json.get(key).getAsString();
    repo.updateCopyright(fieldValue);
  }
}
