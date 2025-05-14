package com.example.lotteon.entity.admin.banner;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Document(collection = "banner")
@NoArgsConstructor
@AllArgsConstructor
public class BannerDocument {

  @Id
  @Field("_id")
  @SerializedName("_id")
  private String _id;

  @Field("id")
  @SerializedName("id")
  private String id;

  @Field("banner_info")
  @SerializedName("banner_info")
  private BannerInfo bannerInfo;

  @Field("position")
  @SerializedName("position")
  private String position;

  @Field("location")
  @SerializedName("location")
  private String location;

  @Field("start")
  @SerializedName("start")
  private LocalDateTime start;

  @Field("expiration")
  @SerializedName("expiration")
  private LocalDateTime expiration;

  @Field("status")
  @SerializedName("status")
  private String status = "inactive";
}
