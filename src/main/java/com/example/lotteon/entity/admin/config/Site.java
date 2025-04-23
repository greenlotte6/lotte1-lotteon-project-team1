package com.example.lotteon.entity.admin.config;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Site {

  @Field(name = "title")
  @SerializedName("title")
  private String title;

  @Field(name = "subtitle")
  @SerializedName("subtitle")
  private String subtitle;
}
