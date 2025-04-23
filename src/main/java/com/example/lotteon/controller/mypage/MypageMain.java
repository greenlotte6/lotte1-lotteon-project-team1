package com.example.lotteon.controller.mypage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MypageMain {

    @GetMapping("/mypage")
    public String mypageMain() {
        return "/myPage/mypageMain";
    }


    // 전체주문내역
    @GetMapping("/mypage/wholeorder")
    public String wholeorder() {
        return "/myPage/wholeorder";
    }

    // 포인트 내역
    @GetMapping("/mypage/point")
    public String point() {
        return "/myPage/point";
    }

    // 쿠폰 내역
    @GetMapping("/mypage/coupon")
    public String coupon() {
        return "/myPage/coupon";
    }

    // 나의 리뷰
    @GetMapping("/mypage/reviews")
    public String reviews() {
        return "/myPage/review";
    }

    // 문의하기
    @GetMapping("/mypage/qna")
    public String qna() {
        return "/myPage/qna";
    }

    // 나의 설정
    @GetMapping("/mypage/setting")
    public String setting() {
        return "/myPage/setting";
    }
}
