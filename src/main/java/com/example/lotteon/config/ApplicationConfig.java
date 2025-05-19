package com.example.lotteon.config;

import com.example.lotteon.entity.product.Product;
import com.example.lotteon.es.document.ProductDocument;
import com.example.lotteon.interceptor.GlobalHitCounter;
import com.example.lotteon.redis.repository.GlobalHitRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class ApplicationConfig {

  @Bean
  public ModelMapper modelMapper() {

    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
        .setMatchingStrategy(MatchingStrategies.STRICT)
        .setFieldMatchingEnabled(true);

    // Date → LocalDateTime 커스텀 컨버터
    Converter<Date, LocalDateTime> dateToLocalDateTimeConverter = new Converter<>() {
      @Override
      public LocalDateTime convert(MappingContext<Date, LocalDateTime> context) {
        Date source = context.getSource();
        return source == null ? null : source.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
      }
    };

    // 전역 매핑 설정
    modelMapper.addConverter(dateToLocalDateTimeConverter, Date.class, LocalDateTime.class);

    modelMapper.addMappings(new PropertyMap<Product, ProductDocument>() {
      @Override
      protected void configure() {
        map(source.getId(), destination.getId());
        map(source.getCategory().getId(), destination.getCategoryId());
        map(source.getSubCategory().getId(), destination.getSubCategoryId());
        map(source.getName(), destination.getName());
        map(source.getDescription(), destination.getDescription());
        map(source.getSeller().getSellerId().getUser().getId(), destination.getSellerId());
        map(source.getPrice(), destination.getPrice());
        map(source.getPoint(), destination.getPoint());
        map(source.getDiscountRate(), destination.getDiscountRate());
        map(source.getStock(), destination.getStock());
        map(source.getDeliveryFee(), destination.getDeliveryFee());
        map(source.getImage().getId(), destination.getImageId());
        map(source.getStatus(), destination.getStatus());
        map(source.isVatFree(), destination.isVatFree());
        map(source.getBusinessClassification(), destination.getBusinessClassification());
        map(source.isReceiptIssuable(), destination.isReceiptIssuable());
        map(source.getOrigin(), destination.getOrigin());
        map(source.getQuality(), destination.getQuality());
      }
    });

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
          private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
              "yyyy-MM-dd");

          @Override
          public JsonElement serialize(LocalDate localDate, Type typeOfSrc,
              JsonSerializationContext context) {
            return new JsonPrimitive(localDate.format(formatter));
          }
        })
        .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
          private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
              "yyyy-MM-dd");

          @Override
          public LocalDate deserialize(JsonElement json, Type typeOfT,
              JsonDeserializationContext context) {
            return LocalDate.parse(json.getAsString(), formatter);
          }
        }).registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX") // 다른 날짜 형식이 필요한 경우
        .create();
  }


  @Bean
  public MongoTemplate mongoTemplate(MongoDatabaseFactory dbFactory,
      MongoMappingContext context,
      MongoCustomConversions conversions) {

    MappingMongoConverter converter = new MappingMongoConverter(
        dbFactory, context
    );
    converter.setCustomConversions(conversions);

    // 👇 _class 필드 제거
    converter.setTypeMapper(new DefaultMongoTypeMapper(null));

    converter.afterPropertiesSet();

    return new MongoTemplate(dbFactory, converter);
  }
}
