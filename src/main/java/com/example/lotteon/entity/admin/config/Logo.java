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
public class Logo {

  @Field(name = "header_location")
  private String headerLogoLocation;

  @Field(name = "footer_location")
  private String footerLogoLocation;

  @Field(name = "favicon")
  private String faviconLocation;
}
