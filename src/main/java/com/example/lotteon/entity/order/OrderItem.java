package com.example.lotteon.entity.order;

import com.example.lotteon.entity.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "order_item")
public class OrderItem {

  @Id
  private int id;

  @JoinColumn(name = "order_number")
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  private Order order;

  @JoinColumn(name = "product_id")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Product product;

  @Column(name = "amount")
  private int amount;

}
