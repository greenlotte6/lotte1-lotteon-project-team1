package com.example.lotteon.repository.coupon;

import com.example.lotteon.entity.coupon.Coupon;
import com.example.lotteon.entity.coupon.Coupon_History;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.repository.coupon.custom.CouponHistoryRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponHistoryRepository extends JpaRepository<Coupon_History, Integer>, CouponHistoryRepositoryCustom {
    boolean existsByMemberAndCoupon(Member member, Coupon coupon);

    //List<Coupon_History> findByMember_MemberId_User_Id(String userId);
    Page<Coupon_History> findByMember_MemberId_User_Id(String userId, Pageable pageable);
}
