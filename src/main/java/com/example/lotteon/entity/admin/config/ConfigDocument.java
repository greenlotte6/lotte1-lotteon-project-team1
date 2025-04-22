package com.example.lotteon.entity.admin.config;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "admin")
@NoArgsConstructor
@AllArgsConstructor
public class ConfigDocument {

  @Id
  private String id;

  @Field(name = "version")
  private String version;

  @Field(name = "site")
  private Site site;

  @Field(name = "logo")
  private Logo logo;

  @Field(name = "corp_info")
  private CorpInfo corpInfo;

  @Field(name = "cs_info")
  private CustomerServiceInfo csInfo;

  @Field(name = "copyright")
  private String copyright;

}
