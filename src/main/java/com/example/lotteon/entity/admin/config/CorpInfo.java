package com.example.lotteon.entity.admin.config;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorpInfo {

  @Field(name = "name")
  @SerializedName("name")
  private String name;

  @Field(name = "ceo")
  @SerializedName("ceo")
  private String ceo;

  @Field(name = "business_num")
  @SerializedName("business_num")
  private String businessNumber;

  @Field(name = "seller_num")
  @SerializedName("seller_num")
  private String sellerNumber;

  @Field(name = "address")
  @SerializedName("address")
  private String address;

  @Field(name = "address_detail")
  @SerializedName("address_detail")
  private String addressDetail;
}
