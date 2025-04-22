package com.example.lotteon.entity.admin.config;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document
public class CorpInfo {

  @Field(name = "name")
  private String name;

  @Field(name = "ceo")
  private String ceo;

  @Field(name = "business_number")
  private String businessNumber;

  @Field(name = "seller_number")
  private String sellerNumber;

  @Field(name = "address")
  private String address;

  @Field(name = "address_detail")
  private String addressDetail;

}
