package com.example.lotteon.config;

import com.example.lotteon.interceptor.GlobalHitCounter;
import com.example.lotteon.redis.repository.GlobalHitRepository;
import com.google.gson.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationConfig {

  @Bean
  public ModelMapper modelMapper() {

    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
        .setMatchingStrategy(MatchingStrategies.STRICT)
        .setFieldMatchingEnabled(true);

    return modelMapper;
  }

  @Bean
  public GlobalHitCounter globalHitCounter(GlobalHitRepository repository) {
    return new GlobalHitCounter(repository);
  }

  @Bean
  public Gson gson() {
    // LocalDate를 처리할 TypeAdapter 정의
    return new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
              private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

              @Override
              public JsonElement serialize(LocalDate localDate, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(localDate.format(formatter));
              }
            })
            .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
              private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

              @Override
              public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
                return LocalDate.parse(json.getAsString(), formatter);
              }
            })
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX") // 다른 날짜 형식이 필요한 경우
            .create();
  }
}
