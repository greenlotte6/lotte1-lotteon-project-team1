package com.example.lotteon.config;

import com.example.lotteon.service.es.ProductSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartupRunner implements ApplicationRunner {

  private final ProductSyncService service;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("LotteonApplication started");
    log.info("Synchronizing products from MySQL to ElasticSearch");
    service.syncAll();
  }
}
