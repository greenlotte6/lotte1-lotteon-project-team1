package com.example.lotteon.entity.order;

import com.example.lotteon.entity.user.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
public class Order {

  @Id
  @Column(name = "order_number")
  private String orderNumber;

  @JoinColumn(name = "member_id")
  @ManyToOne(optional = false)
  private Member member;

  @Column(name = "payment")
  private String payment;

  @JoinColumn(name = "status_id")
  @ManyToOne(optional = false)
  private OrderStatus status;

  @Column(name = "order_date")
  private LocalDate orderDate;
}
