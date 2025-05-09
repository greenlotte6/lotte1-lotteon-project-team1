package com.example.lotteon.entity.admin.config;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionConfig {

  @Id
  @Field("_id")
  private String _id;

  @Field("id")
  @SerializedName("id")
  private String id;

  @Field("version")
  @SerializedName("version")
  private String version;

  @Field("author")
  @SerializedName("author")
  private String author;

  @Field("created_at")
  @SerializedName("created_at")
  private Date createdAt;

  @Field("description")
  @SerializedName("description")
  private String description;
}
