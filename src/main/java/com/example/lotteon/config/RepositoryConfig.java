package com.example.lotteon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.lotteon.repository.jpa")
@EnableElasticsearchRepositories(basePackages = "com.example.lotteon.repository.es")
public class RepositoryConfig {

}
