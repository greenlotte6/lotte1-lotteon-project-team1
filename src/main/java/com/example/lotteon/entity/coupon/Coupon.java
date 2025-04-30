package com.example.lotteon.entity.coupon;

import com.example.lotteon.entity.user.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "coupon")
public class Coupon {

    private static final String[] STATUSES = {"issued", "used"};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private Coupon_Type coupon_type;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benefit_id")
    private Coupon_Benefit coupon_benefit;

    private String from;
    private String to;
    private String seller_id;
    private int issued_amount;
    private int used_amount;
    private String status;
    private String description;

    @CreationTimestamp
    private LocalDateTime issued_date;

}
