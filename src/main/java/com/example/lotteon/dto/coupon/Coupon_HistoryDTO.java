package com.example.lotteon.dto.coupon;

import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.entity.coupon.Coupon;
import com.example.lotteon.entity.user.Member;
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

    private CouponDTO coupon;
    private MemberDTO member;
}
