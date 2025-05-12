package com.example.lotteon.controller.api.admin.config;

import com.example.lotteon.entity.admin.config.CorpInfo;
import com.example.lotteon.entity.admin.config.CustomerServiceInfo;
import com.example.lotteon.entity.admin.config.Logo;
import com.example.lotteon.entity.admin.config.Site;
import com.example.lotteon.service.admin.BasicConfigService;
import com.example.lotteon.service.admin.CacheService;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/admin/config/basic")
@RequiredArgsConstructor
public class BasicConfigRestController {

  @Value("${lotteon.upload.path}")
  private String uploadPath;

  private final Gson gson;
  private final BasicConfigService service;
  private final CacheService cacheService;

  private Logo doUpload(List<MultipartFile> images, List<String> metadata) throws IOException {
    int idx = 0;
    Logo updatedLogo = null;
    for (MultipartFile image : images) { //전송된 이미지를 /var/www/upload에 저장
      if (!image.isEmpty()) {
        String imageName = image.getOriginalFilename();
        File dest = new File(uploadPath + "/" + imageName);
        image.transferTo(dest);

        String currentMetaData = metadata.get(idx);
        if (currentMetaData.contains("header")) {
          updatedLogo = service.updateLogoBy("header_location", "/upload/" + imageName);
        } else if (currentMetaData.contains("footer")) {
          updatedLogo = service.updateLogoBy("footer_location", "/upload/" + imageName);
        } else if (currentMetaData.contains("favicon")) {
          updatedLogo = service.updateLogoBy("favicon_location", "/upload/" + imageName);
        }
      }
      idx++;
    }
    return updatedLogo;
  }

  @PutMapping("/site")
  public ResponseEntity<String> updateSite(@RequestBody Site config,
      @RequestParam String key) {
    log.info("PUT request for {} with {}", key, config.toString());

    service.updateSite(config);

    cacheService.invalidateCache();
    return new ResponseEntity<>("{'status': 'ok'}", HttpStatus.OK);
  }

  @PutMapping("/corp")
  public ResponseEntity<String> updateCorpInfo(@RequestBody CorpInfo config,
      @RequestParam String key) {
    log.info("PUT request for {} with {}", key, config.toString());
    service.updateCorpInfo(config);
    cacheService.invalidateCache();
    return new ResponseEntity<>("{}", HttpStatus.OK);
  }

  @PutMapping("/cs")
  public ResponseEntity<String> updateCsInfo(@RequestBody CustomerServiceInfo config,
      @RequestParam String key) {
    log.info("PUT request for {} with {}", key, config.toString());
    service.updateCsInfo(config);

    cacheService.invalidateCache();
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }

  @PutMapping("/copyright")
  public ResponseEntity<String> updateCopyright(@RequestBody String copyright,
      @RequestParam String key) {
    log.info("PUT request for {} with {}", key, copyright);
    service.updateCopyright(key, copyright);

    cacheService.invalidateCache();
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }

  @PostMapping("/upload")
  public ResponseEntity<String> upload(
      @RequestParam(value = "images") List<MultipartFile> images,
      @RequestParam(value = "metadata") List<String> metadata) {
    String responseBody = "";
    try {
      Logo updatedLogo = doUpload(images, metadata);
      responseBody = gson.toJson(updatedLogo);
    } catch (IOException e) {
      log.warn("Could not transfer file \"{}\"", e.getMessage());
      return new ResponseEntity<>("Could not transfer file\n " + e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

    cacheService.invalidateCache();
    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }
}
