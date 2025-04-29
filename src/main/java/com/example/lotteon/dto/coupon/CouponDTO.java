package com.example.lotteon.dto.coupon;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CouponDTO {

    private static final String[] STATUSES = {"issued", "used"};

    private int id;
    private int type_id;
    private String name;
    private int benefit_id;
    private String from;
    private String to;
    private String seller_id;
    private int issued_amount;
    private int used_amount;
    private String status;
    private String description;
    private String issued_date;
}
