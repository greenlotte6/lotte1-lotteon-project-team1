package com.example.lotteon.service.admin;

import com.example.lotteon.entity.admin.config.CorpInfo;
import com.example.lotteon.entity.admin.config.CustomerServiceInfo;
import com.example.lotteon.entity.admin.config.Site;
import com.example.lotteon.repository.admin.AdminConfigRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminConfigService {

  private final AdminConfigRepository repo;
  private final Gson gson;

  public void updateSite(Site config) {
    repo.updateSite(config);
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
