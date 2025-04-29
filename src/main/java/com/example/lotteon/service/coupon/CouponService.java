package com.example.lotteon.service.coupon;

import com.example.lotteon.dto.coupon.CouponDTO;
import com.example.lotteon.entity.coupon.Coupon;
import com.example.lotteon.entity.coupon.Coupon_Benefit;
import com.example.lotteon.entity.coupon.Coupon_Type;
import com.example.lotteon.repository.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final ModelMapper modelMapper;

    public int couponRegister(CouponDTO couponDTO) {

        Coupon coupon = Coupon.builder()
                .coupon_type(Coupon_Type.builder().id(couponDTO.getType_id()).build())
                .name(couponDTO.getName())
                .coupon_benefit(Coupon_Benefit.builder().id(couponDTO.getBenefit_id()).build())
                .from(couponDTO.getFrom())
                .to(couponDTO.getTo())
                .seller_id(couponDTO.getSeller_id())
                .issued_amount(couponDTO.getIssued_amount())
                .used_amount(couponDTO.getUsed_amount())
                .status("issued")
                .description(couponDTO.getDescription())
                .build();

        //JPA 저장
        Coupon savedCoupon = couponRepository.save(coupon);

        //저장한 글번호 반환
        return savedCoupon.getId();

    }
}
