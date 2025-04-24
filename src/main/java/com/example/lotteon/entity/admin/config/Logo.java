package com.example.lotteon.entity.admin.config;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Logo {

  @Field(name = "header_location")
  @SerializedName("header_location")
  private String headerLogoLocation;

  @Field(name = "footer_location")
  @SerializedName("footer_location")
  private String footerLogoLocation;

  @Field(name = "favicon_location")
  @SerializedName("favicon_location")
  private String faviconLocation;
}
