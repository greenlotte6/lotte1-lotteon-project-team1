package com.example.lotteon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//TODO: Delete exclude value below
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LotteonApplication {

  public static void main(String[] args) {
    SpringApplication.run(LotteonApplication.class, args);
  }

}
