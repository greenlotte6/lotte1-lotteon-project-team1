package com.example.lotteon.entity.product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CartId.class)
public class Cart {

    @Id
    @Column(name = "member_id")
    private String memberId;

    @Id
    @Column(name = "product_id")
    private int productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @CreationTimestamp
    private LocalDate registerDate;

    private int quantity;
    private int price;
    private int totalPrice;
    private int dil;

    public void updateQuantity(int newQuantity, int pricePerItem) {
        this.quantity = newQuantity;
        this.totalPrice = newQuantity * pricePerItem;
    }
}

