package com.example.lotteon.entity.admin.banner;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerInfo {

  @Field("title")
  @SerializedName("title")
  private String title;

  @Field("width")
  @SerializedName("width")
  private double width;

  @Field("height")
  @SerializedName("height")
  private double height;

  @Field("background_color")
  @SerializedName("background_color")
  private String backgroundColor;

  @Field("url")
  @SerializedName("url")
  private String url;
}
