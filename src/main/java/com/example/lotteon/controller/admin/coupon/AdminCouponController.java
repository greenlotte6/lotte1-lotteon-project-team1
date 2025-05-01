package com.example.lotteon.controller.admin.coupon;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.dto.PageResponseDTO;
import com.example.lotteon.dto.coupon.CouponDTO;
import com.example.lotteon.dto.coupon.Coupon_HistoryDTO;
import com.example.lotteon.dto.cs.QnaDTO;
import com.example.lotteon.dto.cs.ReplyDTO;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.service.coupon.CouponHistoryService;
import com.example.lotteon.service.coupon.CouponService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap; //

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/coupon")
public class AdminCouponController {

    private final HttpServletRequest request;
    private final CouponService couponService;
    private final CouponHistoryService couponHistoryService;
    private final Gson gson;

    @GetMapping("/search")
    public String searchcoupon(Model model, PageRequestDTO pageRequestDTO) {

        //리스트 출력
        int id = pageRequestDTO.getId();
        PageResponseDTO pageResponseDTO = couponService.searchAll(pageRequestDTO, id);

        model.addAttribute(pageResponseDTO);


        return "/admin/coupon/searchcoupon";
    }


    @GetMapping("/coupon")
    public String coupon(Model model, PageRequestDTO pageRequestDTO) {

        //리스트 출력
        int id = pageRequestDTO.getId();
        PageResponseDTO pageResponseDTO = couponService.findAll(pageRequestDTO, id);

        model.addAttribute(pageResponseDTO);


        return "/admin/coupon/coupon";
    }

    @GetMapping(value = "/coupon/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCouponDetail(@PathVariable("id") int id) {
        CouponDTO coupon = couponService.findById(id);
        Map<String, Object> result = new HashMap<>();
        if (coupon == null) {
            result.put("success", false);
            result.put("message", "쿠폰을 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to find coupon");
        }
        result.put("success", true);
        result.put("coupon", coupon);
        String json = gson.toJson(coupon);
        return ResponseEntity.ok(json);
    }



    @PostMapping("/coupon/register")
    public String couponRegister(CouponDTO couponDTO) {

        int id = couponService.couponRegister(couponDTO);

        return "redirect:/admin/coupon/coupon";
    }

    //종료 버튼
    @PostMapping("/update-status/{couponId}")
    @ResponseBody
    public Map<String, Object> updateCouponStatus(@PathVariable("couponId") int couponId) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean success = couponService.updateCouponStatus(couponId);
            response.put("success", success);
        } catch (Exception e) {
            log.error("쿠폰 상태 업데이트 오류: ", e);
            response.put("success", false);
        }

        return response;
    }








    @GetMapping("/issued")
    public String issued() {
        return "/admin/coupon/issued";
    }

    @PostMapping("/couponHistory/register")
    public String couponHistoryRegister(Member member) {

        int id = couponHistoryService.couponHistoryRegister(member);

        return "redirect:/admin/coupon/issued";
    }

}
