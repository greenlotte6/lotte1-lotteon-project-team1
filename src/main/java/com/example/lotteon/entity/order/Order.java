package com.example.lotteon.entity.order;

import com.example.lotteon.entity.product.Product;
import com.example.lotteon.entity.user.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "order_number")
  private String orderNumber;

  @JoinColumn(name = "member_id")
  @ManyToOne
  private Member member;

  @JoinColumn(name = "product_id")
  @ManyToOne
  private Product product;

  @Column(name = "amount")
  private int amount; //주문하는 상품의 개수(ex. 맨투맨 6개 => amount = 6)

  @Column(name = "payment")
  private String payment;

  @JoinColumn(name = "status_id")
  @ManyToOne
  private OrderStatus status;

  @Column(name = "order_date")
  private LocalDate orderDate;
}
