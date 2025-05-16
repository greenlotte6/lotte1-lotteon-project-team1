package com.example.lotteon.service.admin;

import com.example.lotteon.dto.product.ProductCategoryDTO;
import com.example.lotteon.dto.product.ProductSubCategoryDTO;
import com.example.lotteon.entity.admin.banner.BannerDocument;
import com.example.lotteon.entity.admin.config.ConfigDocument;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisKeyCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
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

  public List<BannerDocument> getCachedBanner(String position) {
    String prefix = "admin::banner::" + position + "::";
    Set<String> keys = new HashSet<>();
    ScanOptions options = ScanOptions.scanOptions()
        .match(prefix + "*")
        .count(10)
        .build();
    template.execute((RedisConnection connection) -> {
      RedisKeyCommands keyCommands = connection.keyCommands();
      try (Cursor<byte[]> cursor = keyCommands.scan(options)) {
        while (cursor.hasNext()) {
          keys.add(new String(cursor.next()));//복수의 일치하는 Redis 키 값 저장
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    });

    if (keys.isEmpty()) {// 일치하는 key가 하나도 없는 경우
      return null; // null 반환
    }

    ValueOperations<String, String> ops = template.opsForValue();
    List<String> cachedData = ops.multiGet(keys);

    List<BannerDocument> banners = new ArrayList<>();
    if (cachedData != null) {// 키가 존재하고, 데이터가 존재하는 경우
      for (String data : cachedData) {
        if (data == null) { //특정 키에 대한 데이터가 존재하지 않는 경우
          continue; //스킵
        }
        banners.add(gson.fromJson(data, BannerDocument.class));//존재하는 경우, DTO로 변환 후 리스트에 추가
      }
      return banners;
    }

    return null;
  }

  public void cacheBanner(BannerDocument banner) {
    //hash format = admin::banner::position::_id
    String hash = String.format("admin::banner::%s::%s", banner.getPosition(), banner.get_id());

    LocalDateTime now = LocalDateTime.now();
    long ttl =
        banner.getExpiration().toInstant(ZoneOffset.UTC).getEpochSecond() - now.toInstant(
            ZoneOffset.UTC).getEpochSecond();

    ValueOperations<String, String> ops = template.opsForValue();
    log.info("Caching banner with hash '{}'...", hash);
    String serializedData = gson.toJson(banner);
    ops.set(hash, serializedData, ttl, TimeUnit.SECONDS);
    log.info("Cached banner with hash '{}'", hash);
  }

  public void invalidateBannerCache(String _id, String position) {
    String hash = String.format("admin::banner::%s::%s", position, _id);
    template.delete(hash);
  }

  public void invalidateBannerCache(String _id) {
    Set<String> keysToDelete = new HashSet<>();
    String pattern = String.format("admin::banner::*::%s", _id);

    template.execute((RedisConnection conn) -> {
      Cursor<byte[]> cursor = conn.scan(ScanOptions.scanOptions().match(pattern).count(10).build());
      while (cursor.hasNext()) {
        String key = new String(cursor.next());
        keysToDelete.add(key);
      }
      return null;
    });

    template.delete(keysToDelete);
  }
}
