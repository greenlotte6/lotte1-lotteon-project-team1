package com.example.lotteon.service.admin;

import com.example.lotteon.dto.product.ProductCategoryDTO;
import com.example.lotteon.dto.product.ProductSubCategoryDTO;
import com.example.lotteon.entity.admin.config.ConfigDocument;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
    log.info("caching admin::basic_config...");
    ops.set("admin::basic_config", serializedConfig);
    log.info("Caching complete.\n {}", serializedConfig);
  }

  public void invalidateCache() {
    template.delete("admin::basic_config");
    log.info("Cache \"admin::basic_config\" has been invalidated");
  }

  public List<ProductCategoryDTO> getCachedCategory() {
    ValueOperations<String, String> ops = template.opsForValue();
    String cachedData = ops.get("product::categories");
    Type categoryListType = new TypeToken<List<ProductCategoryDTO>>() {
    }.getType();
    if (cachedData != null) {
      return gson.fromJson(cachedData, categoryListType);
    }
    return null;
  }

  public List<ProductSubCategoryDTO> getCachedSubCategory() {
    ValueOperations<String, String> ops = template.opsForValue();
    String cachedData = ops.get("product::sub_categories");
    Type subCategoryListType = new TypeToken<List<ProductSubCategoryDTO>>() {
    }.getType();
    if (cachedData != null) {
      return gson.fromJson(cachedData, subCategoryListType);
    }
    return null;
  }

  public void cacheCategory(List<ProductCategoryDTO> categories) {
    String serializedCategory = gson.toJson(categories);

    ValueOperations<String, String> ops = template.opsForValue();
    log.info("Caching product::category...");
    ops.set("product::categories", serializedCategory, 1, TimeUnit.HOURS);//TTL = 1시간
    log.info("Cached product::category");

  }

  public void cacheSubCategory(List<ProductSubCategoryDTO> subCategories) {
    String serializedSubCategory = gson.toJson(subCategories);

    ValueOperations<String, String> ops = template.opsForValue();
    log.info("Caching product::sub_category...");
    ops.set("product::sub_categories", serializedSubCategory, 1, TimeUnit.HOURS);//TTL = 1시간
    log.info("Cached product::sub_categories");
  }

  public void invalidateCategoryCache() {
    template.delete("product::categories");
    log.info("Cache \"product::categories\" has been invalidated");
  }

  public void invalidateSubCategoryCache() {
    template.delete("product::sub_categories");
    log.info("Cache \"product::sub_categories\" has been invalidated");
  }
}
