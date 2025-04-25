package com.example.lotteon.service.admin;

import com.example.lotteon.entity.admin.config.ConfigDocument;
import com.example.lotteon.entity.admin.config.CorpInfo;
import com.example.lotteon.entity.admin.config.CustomerServiceInfo;
import com.example.lotteon.entity.admin.config.Logo;
import com.example.lotteon.entity.admin.config.Site;
import com.example.lotteon.entity.admin.config.VersionConfig;
import com.example.lotteon.exception.NoDocumentFoundException;
import com.example.lotteon.repository.admin.config.BasicConfigRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicConfigService {

  private final CacheService cacheService;
  private final RedisTemplate<String, String> template;
  private final BasicConfigRepository repo;
  private final Gson gson;

  public ConfigDocument getConfig() {
    ConfigDocument config = repo.find();
    String serializedConfig = gson.toJson(config);
    cacheService.cache(serializedConfig);
    return config;
  }

  public List<VersionConfig> getAllVersions() {
    return repo.findAllVersion();
  }

  public VersionConfig getVersionByName(String name) throws NoDocumentFoundException {
    return repo.findVersionByName(name);
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
