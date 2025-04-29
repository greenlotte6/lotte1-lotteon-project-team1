package com.example.lotteon.dto.coupon;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Coupon_HistoryDTO {

    private static final String[] STATUSES = {"used", "unused"};

    private int id;
    private int coupon_id;
    private String user_id;
    private String status;
    private String used_date;
}
