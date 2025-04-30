package com.example.lotteon.dto.coupon;

import com.example.lotteon.entity.coupon.Coupon_Benefit;
import com.example.lotteon.entity.coupon.Coupon_Type;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private Coupon_BenefitDTO coupon_benefit;
    private Coupon_TypeDTO coupon_type;

    public void setIssued_date(LocalDateTime issued_date) {
        if (issued_date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.issued_date = issued_date.format(formatter);
        }
    }
}
