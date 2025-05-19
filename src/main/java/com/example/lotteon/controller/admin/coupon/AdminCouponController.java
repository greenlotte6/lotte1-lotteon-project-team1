package com.example.lotteon.controller.admin.coupon;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.dto.PageResponseDTO;
import com.example.lotteon.dto.coupon.CouponDTO;
import com.example.lotteon.dto.coupon.Coupon_HistoryDTO;
import com.example.lotteon.entity.coupon.Coupon;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.repository.jpa.coupon.CouponHistoryRepository;
import com.example.lotteon.repository.jpa.coupon.CouponRepository;
import com.example.lotteon.service.coupon.CouponHistoryService;
import com.example.lotteon.service.coupon.CouponService;
import com.example.lotteon.service.user.MemberService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/coupon")
public class AdminCouponController {

  private final HttpServletRequest request;
  private final CouponService couponService;
  private final CouponHistoryService couponHistoryService;
  private final Gson gson;
  private final MemberService memberService;
  private final CouponHistoryRepository couponHistoryRepository;
  private final CouponRepository couponRepository;

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


  //쿠폰 발급 현황
  @GetMapping("/issued")
  public String issued(Model model, PageRequestDTO pageRequestDTO) {

    //리스트 출력
    int id = pageRequestDTO.getId();
    PageResponseDTO pageResponseDTO = couponHistoryService.findAll(pageRequestDTO, id);

    model.addAttribute(pageResponseDTO);

    return "/admin/coupon/issued";
  }


  @GetMapping("/issued/search")
  public String searchCouponHistory(Model model, PageRequestDTO pageRequestDTO) {

    //리스트 출력
    int id = pageRequestDTO.getId();
    PageResponseDTO pageResponseDTO = couponHistoryService.searchAll(pageRequestDTO, id);

    model.addAttribute(pageResponseDTO);

    return "/admin/coupon/searchissued";
  }


  //회원가입 시 쿠폰 증정  >> 쿠폰 받기 클릭시 증정은 productController에 있음.
  @PostMapping("/couponHistory/register")
  public String couponHistoryRegister(Member member) {

    try {
      // 회원가입 쿠폰(101번) 지급
      Coupon coupon = couponRepository.findById(101)
          .orElseThrow(() -> new IllegalStateException("회원가입 쿠폰을 찾을 수 없습니다."));

      // 쿠폰 발급 내역 생성
      couponHistoryService.couponHistoryRegister(member, coupon); // 101번 쿠폰 지급
    } catch (Exception e) {
      return "redirect:/error"; // 쿠폰 지급 오류 처리
    }

    return "redirect:/admin/coupon/issued";
  }

  //쿠폰 발급내역 상세보기
  @GetMapping(value = "/issued/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> getCouponHistoryDetail(@PathVariable("id") int id) {
    Coupon_HistoryDTO couponHistory = couponHistoryService.findById(id);
    Map<String, Object> result = new HashMap<>();
    if (couponHistory == null) {
      result.put("success", false);
      result.put("message", "쿠폰을 찾을 수 없습니다.");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to find couponHistory");
    }
    result.put("success", true);
    result.put("couponHistory", couponHistory);
    String json = gson.toJson(couponHistory);
    return ResponseEntity.ok(json);
  }

  //쿠폰 발급현황 중지 버튼 활성화
  @PostMapping("/issued/{id}")
  @ResponseBody
  public ResponseEntity<?> useCoupon(@PathVariable("id") int id) {
    couponHistoryService.markAsUsed(id);
    return ResponseEntity.ok(Map.of("status", "success"));
  }


}
