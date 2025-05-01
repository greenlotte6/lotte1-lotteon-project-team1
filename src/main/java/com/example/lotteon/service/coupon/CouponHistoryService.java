package com.example.lotteon.service.coupon;

import com.example.lotteon.dto.coupon.Coupon_HistoryDTO;
import com.example.lotteon.entity.coupon.Coupon;
import com.example.lotteon.entity.coupon.Coupon_History;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.entity.user.MemberId;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.repository.coupon.CouponHistoryRepository;
import com.example.lotteon.repository.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponHistoryService {

    private final CouponHistoryRepository couponHistoryRepository;
    private final CouponRepository couponRepository;
    private final ModelMapper modelMapper;

    public int couponHistoryRegister(Member member) {
        // "회원" 쿠폰 조회
        Coupon coupon = couponRepository.findFirstByNameContaining("가입");

        if (coupon == null) {
            throw new IllegalStateException("No coupon found for user registration.");
        }

        // 쿠폰 발급 내역 생성
        Coupon_History couponHistory = Coupon_History.builder()
                .coupon(coupon)
                .member(member)
                .status("unused")
                .build();

        // 저장
        Coupon_History savedCouponHistory = couponHistoryRepository.save(couponHistory);
        return savedCouponHistory.getId();
    }
}
