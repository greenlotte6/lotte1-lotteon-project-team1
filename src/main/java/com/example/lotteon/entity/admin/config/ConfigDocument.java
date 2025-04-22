package com.example.lotteon.entity.admin.config;

import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class ConfigDocument {

  @Id
  private UUID id;

  @Field(name = "version")
  private VersionInformation version;

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
