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
public class CorpInfo {

  @Field(name = "name")
  private String name;

  @Field(name = "ceo")
  private String ceo;

  @Field(name = "business_num")
  private String businessNumber;

  @Field(name = "seller_num")
  private String sellerNumber;

  @Field(name = "address")
  private String address;

  @Field(name = "address_detail")
  private String addressDetail;

}
