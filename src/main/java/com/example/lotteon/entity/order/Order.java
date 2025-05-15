package com.example.lotteon.entity.order;

import com.example.lotteon.entity.user.Member;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`order`")
public class Order {

  @Id
  @Column(name = "order_number")
  @SerializedName("order_number")
  private String orderNumber;

  @JoinColumn(name = "member_id")
  @ManyToOne(optional = false)
  @SerializedName("member")
  private Member member;

  @Column(name = "payment")
  @SerializedName("payment")
  private String payment;

  @Column(name = "recipient_name")
  @SerializedName("recipient_name")
  private String recipientName;

  @Column(name = "recipient_contact")
  @SerializedName("recipient_contact")
  private String recipientContact;

  @Column(name = "recipient_zip")
  @SerializedName("recipient_zip")
  private String recipientZip;

  @Column(name = "recipient_address")
  @SerializedName("recipient_address")
  private String recipientAddress;

  @Column(name = "recipient_address_detail")
  @SerializedName("recipient_address_detail")
  private String recipientAddressDetail;

  @Column(name = "description")
  @SerializedName("description")
  private String description;

  @JoinColumn(name = "status_id")
  @ManyToOne(optional = false)
  @SerializedName("status")
  private OrderStatus status;

  @Column(name = "order_date")
  @SerializedName("order_date")
  private LocalDate orderDate;
}
