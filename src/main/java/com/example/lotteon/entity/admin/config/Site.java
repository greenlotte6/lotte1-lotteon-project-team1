package com.example.lotteon.entity.admin.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Site {

  @Field(name = "title")
  private String title;

  @Field(name = "subtitle")
  private String subtitle;
}
