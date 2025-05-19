package com.example.lotteon.repository.jpa.coupon;

import com.example.lotteon.entity.coupon.Coupon;
import com.example.lotteon.repository.jpa.coupon.custom.CouponRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Integer>, CouponRepositoryCustom {

  Coupon findByName(String name);

  Coupon findFirstByNameContaining(String name);
}
