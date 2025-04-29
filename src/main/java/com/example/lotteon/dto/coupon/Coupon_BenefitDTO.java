package com.example.lotteon.dto.coupon;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Coupon_BenefitDTO {

    private int id;
    private String benefit; //possible_values =["1000", "2000", "3000", "4000", "5000", "10", "20", "30", "40", "50", "free_delivery"]

}
