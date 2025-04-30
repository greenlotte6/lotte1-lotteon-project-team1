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
@Table(name = "coupon_benefit")
public class Coupon_Benefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String benefit; //possible_values =["1000", "2000", "3000", "4000", "5000", "10", "20", "30", "40", "50", "free_delivery"]
}
