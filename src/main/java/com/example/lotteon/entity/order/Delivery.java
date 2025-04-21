package com.example.lotteon.entity.order;

import com.example.lotteon.entity.user.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

  @Id
  @JoinColumn(name = "order_id")
  @OneToOne
  private Order order;

  @JoinColumn(name = "member_id")
  @ManyToOne
  private Member member;

  @Column(name = "delivery_number")
  private String deliveryNumber;

  @Column(name = "recipient_name")
  private String recipientName;

  @Column(name = "recipient_contact")
  private String recipientContact;

  @Column(name = "recipient_zip")
  private String recipientZip;

  @Column(name = "recipient_address")
  private String recipientAddress;

  @Column(name = "recipient_address_detail")
  private String recipientAddressDetail;

  @Column(name = "description")
  private String description;

  @JoinColumn(name = "delivery_company_id")
  @ManyToOne
  private DeliveryCompany deliveryCompany;

  @JoinColumn(name = "status_id")
  @ManyToOne
  private DeliveryStatus status;
}
