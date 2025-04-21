package com.example.lotteon.entity.order;

import com.example.lotteon.entity.product.Product;
import com.example.lotteon.entity.user.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class Order {

  @Id
  private int id;

  @JoinColumn(name = "member_id")
  @ManyToOne
  private Member member;

  @JoinColumn(name = "delivery_name")
  @OneToOne
  private Delivery delivery;

  @JoinColumn(name = "product_id")
  @ManyToOne
  private Product product;

  @Column(name = "count")
  private int count;

  @Column(name = "price_total")
  private int priceTotal;

  @Column(name = "payment")
  private String payment;

  @JoinColumn(name = "status_id")
  @ManyToOne
  private OrderStatus status;

  @Column(name = "order_date")
  private LocalDate orderDate;
}
