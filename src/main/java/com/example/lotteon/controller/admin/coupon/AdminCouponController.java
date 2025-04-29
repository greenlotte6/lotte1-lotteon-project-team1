package com.example.lotteon.controller.admin.coupon;

import com.example.lotteon.dto.coupon.CouponDTO;
import com.example.lotteon.service.coupon.CouponService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/coupon")
public class AdminCouponController {

    private final HttpServletRequest request;
    private final CouponService couponService;

    @GetMapping("/coupon")
    public String coupon() {
        return "/admin/coupon/coupon";
    }

    @PostMapping("/coupon/register")
    public String couponRegister(CouponDTO couponDTO) {

        int id = couponService.couponRegister(couponDTO);

        return "redirect:/admin/coupon/coupon";
    }






    @GetMapping("/issued")
    public String issued() {
        return "/admin/coupon/issued";
    }

}
