package com.example.lotteon.entity.admin.config;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigDocument {

  @Field(name = "latest_version")
  @SerializedName("latest_version")
  private VersionConfig latestVersion;

  @Field(name = "version")
  @SerializedName("version")
  private List<VersionConfig> versions;

  @Field(name = "site")
  @SerializedName("site")
  private Site site;

  @Field(name = "logo")
  @SerializedName("logo")
  private Logo logo;

  @Field(name = "corp_info")
  @SerializedName("corp_info")
  private CorpInfo corpInfo;

  @Field(name = "cs_info")
  @SerializedName("cs_info")
  private CustomerServiceInfo csInfo;

  @Field(name = "copyright")
  @SerializedName("copyright")
  private String copyright;

}
