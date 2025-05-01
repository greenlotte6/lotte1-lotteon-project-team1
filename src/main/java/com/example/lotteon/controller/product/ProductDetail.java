package com.example.lotteon.controller.product;

import com.example.lotteon.dto.coupon.Coupon_HistoryDTO;
import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.entity.coupon.Coupon;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.repository.coupon.CouponHistoryRepository;
import com.example.lotteon.repository.coupon.CouponRepository;
import com.example.lotteon.service.coupon.CouponHistoryService;
import com.example.lotteon.service.product.ProductDetailService;
import com.example.lotteon.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Slf4j
@Controller
public class ProductDetail {

    private final ProductDetailService productDetailService;
    private final MemberService memberService;
    private final CouponHistoryRepository couponHistoryRepository;
    private final CouponRepository couponRepository;
    private final CouponHistoryService couponHistoryService;


    @GetMapping("/product/detail")
    public String productDetail(@RequestParam("id") int id, Model model) {

        ProductDTO proDTOs = productDetailService.productDetail(id);

        model.addAttribute("prodtos", proDTOs);

        return "/product/proDetail";
    }

    @PostMapping("/product/detail/give-coupon")
    public String giveCoupon(@RequestParam("id") int id, Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        Member member = memberService.findByUserId(username);

        try {
            Coupon coupon = couponRepository.findById(201)
                    .orElseThrow(() -> new IllegalStateException("쿠폰을 찾을 수 없습니다."));

            boolean couponAlreadyGiven = couponHistoryRepository.existsByMemberAndCoupon(member, coupon);
            if (couponAlreadyGiven) {
                redirectAttributes.addFlashAttribute("error", "이미 쿠폰을 받으셨습니다.");
            } else {
                couponHistoryService.couponHistoryRegister(member, coupon);
                redirectAttributes.addFlashAttribute("message", "쿠폰이 성공적으로 지급되었습니다!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "쿠폰 지급에 실패했습니다: " + e.getMessage());
        }

        // id 값을 붙여서 리다이렉트
        return "redirect:/product/detail?id=" + id;
    }


}
