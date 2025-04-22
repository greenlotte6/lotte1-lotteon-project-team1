package com.example.lotteon.entity.admin.config;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class CustomerServiceInfo {

  @Field(name = "contact")
  private String contact;

  @Field(name = "office_hours")
  private String officeHour;

  @Field(name = "email")
  private String email;

  @Field(name = "dispute_officer_contact")
  private String disputeOfficerContact;
}
