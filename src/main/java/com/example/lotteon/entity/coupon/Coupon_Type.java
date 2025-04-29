package com.example.lotteon.entity.coupon;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "coupon_type")
public class Coupon_Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;    // 배송비 무료, 개별상품 할인, 생일 할인 등
}
