package com.example.lotteon.entity.seller;

import com.example.lotteon.entity.order.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sales")
public class Sales {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumns({
      @JoinColumn(name = "seller_id", referencedColumnName = "user_id"),
      @JoinColumn(name = "seller_business_number", referencedColumnName = "business_number")
  })
  private Seller seller;

  @ManyToOne
  @JoinColumn(name = "order_number")
  private Order order;
}
