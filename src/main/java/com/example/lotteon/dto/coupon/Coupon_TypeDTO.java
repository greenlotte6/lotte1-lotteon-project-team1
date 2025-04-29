package com.example.lotteon.dto.coupon;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Coupon_TypeDTO {

    private int id;
    private String name;    // 배송비 무료, 개별상품 할인, 생일 할인 등

}
