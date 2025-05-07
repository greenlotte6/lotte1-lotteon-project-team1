package com.example.lotteon.config;

import com.example.lotteon.interceptor.GlobalHitCounter;
import com.example.lotteon.redis.repository.GlobalHitRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    return new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
          @Override
          public LocalDate deserialize(JsonElement json, Type typeOfT,
              JsonDeserializationContext context)
              throws JsonParseException {
            return LocalDate.parse(json.getAsString());
          }
        })
        .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
          @Override
          public JsonElement serialize(LocalDate src, Type typeOfSrc,
              JsonSerializationContext context) {
            return new JsonPrimitive(src.toString()); // ISO-8601 format (e.g., "2024-05-07")
          }
        })
        .create();
  }
}
