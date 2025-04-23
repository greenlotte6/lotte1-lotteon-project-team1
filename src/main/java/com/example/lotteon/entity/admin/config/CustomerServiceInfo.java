package com.example.lotteon.entity.admin.config;


import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerServiceInfo {

  @Field(name = "contact")
  @SerializedName("contact")
  private String contact;

  @Field(name = "office_hour")
  @SerializedName("office_hour")
  private String officeHour;

  @Field(name = "email")
  @SerializedName("email")
  private String email;

  @Field(name = "dispute_contact")
  @SerializedName("dispute_contact")
  private String disputeContact;
}
