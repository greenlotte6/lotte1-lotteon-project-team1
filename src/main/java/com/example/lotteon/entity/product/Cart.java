package com.example.lotteon.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CartId.class) // ✅ 복합키 클래스 지정
public class Cart {

    @Id
    @Column(name = "member_id")
    private String memberId;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @CreationTimestamp
    private LocalDate registerDate;

    @Column
    private int quantity;

    @Column
    private int price;

    @Column
    private int totalPrice;
}