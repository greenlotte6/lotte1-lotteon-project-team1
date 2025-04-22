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
public class CustomerServiceInfo {

  @Field(name = "contact")
  private String contact;

  @Field(name = "office_hour")
  private String officeHour;

  @Field(name = "email")
  private String email;

  @Field(name = "dispute_contact")
  private String disputeContact;
}
