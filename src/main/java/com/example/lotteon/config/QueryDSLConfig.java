package com.example.lotteon.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDSLConfig {

  @PersistenceContext
  private EntityManager em;

  @Bean
  public JPAQueryFactory queryFactory() {
    return new JPAQueryFactory(em);
  }
}
