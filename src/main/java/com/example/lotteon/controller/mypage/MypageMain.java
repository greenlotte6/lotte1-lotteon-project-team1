package com.example.lotteon.controller.mypage;

import com.example.lotteon.dto.PageResponseDTO;
import com.example.lotteon.dto.coupon.Coupon_HistoryDTO;
import com.example.lotteon.dto.cs.QnaDTO;
import com.example.lotteon.dto.order.MypageOrderWrapper;
import com.example.lotteon.dto.order.OrderWrapper;
import com.example.lotteon.dto.point.PointDTO;
import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.repository.UserRepository;
import com.example.lotteon.repository.order.OrderRepository;
import com.example.lotteon.repository.user.MemberRepository;
import com.example.lotteon.service.admin.point.PointService;
import com.example.lotteon.service.coupon.CouponHistoryService;
import com.example.lotteon.service.cs.QnaService;
import com.example.lotteon.service.cs.ReplyService;
import com.example.lotteon.service.mypage.MyPageService;
import com.example.lotteon.service.order.OrderService;
import com.example.lotteon.service.user.MemberService;
import com.example.lotteon.service.user.UserService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MypageMain {

    private final HttpServletRequest request;
    private final Gson gson;
    private final MemberService memberService;
    private final UserService userService;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final MyPageService myPageService;
    private final PasswordEncoder passwordEncoder;
    private final QnaService qnaService;
    private final ReplyService replyService;
    private final CouponHistoryService couponHistoryService;
    private final PointService pointService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;


    @GetMapping("/mypage")
    public String mypageMain() {
        return "/myPage/mypageMain";
    }


    // 전체주문내역
    @GetMapping("/mypage/wholeorder")
    public String wholeorder(@RequestParam(defaultValue = "0") int page,
                             Model model, Principal principal) {

        String userId = principal.getName();

        Pageable pageable = PageRequest.of(page, 10); // 1페이지에 10개

        Page<MypageOrderWrapper> pages = orderService.findOrderWrappersByUserId(userId, pageable);

        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", page + 1); // 페이지 번호는 1부터 시작 표시

        return "/myPage/wholeorder";
    }

    // 포인트 내역 (마이페이지)
    @GetMapping("/mypage/point")
    public String point(@RequestParam(defaultValue = "0") int page,
                        Model model, Principal principal) {

        String userId = principal.getName();

        Pageable pageable = PageRequest.of(page, 10); // 1페이지에 10개
        Page<PointDTO> pointPage = pointService.findByUserId(userId, pageable);

        model.addAttribute("pages", pointPage.getContent());  // 실제 포인트 내역 리스트
        model.addAttribute("currentPage", pointPage.getNumber());
        model.addAttribute("totalPages", pointPage.getTotalPages());
        model.addAttribute("pageSize", pointPage.getSize());

        return "/myPage/point";
    }

    // 쿠폰 내역
    @GetMapping("/mypage/coupon")
    public String coupon(@RequestParam(defaultValue = "0") int page,
                         Model model, Principal principal) {

        //현재 로그인한 사용자의 ID 가져오기
        String userId = principal.getName();

        Pageable pageable = PageRequest.of(page, 10);
        Page<Coupon_HistoryDTO> couponHistoryDTOPage = couponHistoryService.findByUserId(userId, pageable);

        //List<Coupon_HistoryDTO> couponHistoryDTOList = couponHistoryService.findByUserId(userId);
        model.addAttribute("couponHistoryDTOList", couponHistoryDTOPage.getContent());
        model.addAttribute("currentPage", couponHistoryDTOPage.getNumber());
        model.addAttribute("totalPages", couponHistoryDTOPage.getTotalPages());
        model.addAttribute("pageSize", couponHistoryDTOPage.getSize());

        return "/myPage/coupon";
    }

    // 나의 리뷰
    @GetMapping("/mypage/reviews")
    public String reviews() {
        return "/myPage/review";
    }

    // 문의하기
    @GetMapping("/mypage/qna")
    public String qna(@RequestParam(defaultValue = "0") int page,
                      Model model, Principal principal) {

        //현재 로그인한 사용자의 ID 가져오기
        String userId = principal.getName();

        Pageable pageable = PageRequest.of(page, 10); // 한 페이지당 10개
        Page<QnaDTO> qnaPage = qnaService.findByUserId(userId, pageable);

        //List<QnaDTO> qnaList = qnaService.findByUserId(userId);
        model.addAttribute("qnaList", qnaPage.getContent());
        model.addAttribute("currentPage", qnaPage.getNumber());
        model.addAttribute("totalPages", qnaPage.getTotalPages());
        model.addAttribute("pageSize", qnaPage.getSize());

        return "/myPage/qna";
    }

    // 나의 설정
    @GetMapping("/mypage/setting")
    public String setting(Model model, Principal principal) {

        //현재 로그인한 사용자의 ID 가져오기
        String userId = principal.getName();

        //userId로 회원 정보 조회
        UserDTO userDTO = userService.getUserInfo(userId);
        MemberDTO memberDTO = memberService.findByUserId(userId);

        // 비밀번호 수정 상태 추가
        boolean passwordModified = model.containsAttribute("passwordModified") && (boolean) model.getAttribute("passwordModified");

        model.addAttribute("userDTO", userDTO);
        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("passwordModified", passwordModified);


        return "/myPage/setting";
    }

    // 나의 설정
    @PostMapping("/mypage/setting/modify")
    public String modify(UserDTO userDTO) {
        //서비스 호출
        myPageService.modifyUser(userDTO);

        return "redirect:/mypage/setting";
    }

    // 회원 탈퇴
    @GetMapping("/mypage/setting/delete")
    public String delete(@RequestParam("id") String id, RedirectAttributes redirectAttributes) {
        // 서비스 호출
        myPageService.deleteUser(id);

        // 세션 무효화 및 로그아웃 처리
        try {
            request.logout(); // Spring Security 로그아웃
        } catch (ServletException e) {
            e.printStackTrace();
        }

        // 메시지나 리다이렉트 처리
        redirectAttributes.addFlashAttribute("message", "탈퇴가 완료되었습니다.");
        return "redirect:/";
    }

    //나의설정 > 비밀번호 확인 페이지
    @GetMapping("/mypage/confirmPassword")
    public String confirmPassword(Model model, Principal principal) {

        //현재 로그인한 사용자의 ID 가져오기
        String userId = principal.getName();

        //userId로 회원 정보 조회
        UserDTO userDTO = userService.getUserInfo(userId);
        MemberDTO memberDTO = memberService.findByUserId(userId);

        model.addAttribute("userDTO", userDTO);
        model.addAttribute("memberDTO", memberDTO);

        return "/myPage/confirmPassword";
    }

    @PostMapping("/mypage/confirmPassword")
    public String confirmPassword(@RequestParam("password") String password, Principal principal, RedirectAttributes redirectAttributes) {
        String userId = principal.getName();
        UserDTO savedUser = userService.getUserInfo(userId);

        if (savedUser == null || !passwordEncoder.matches(password, savedUser.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "redirect:/mypage/confirmPassword";
        }

        // 비밀번호가 맞으면 설정 페이지로 리다이렉트하고 비밀번호 수정 상태 전달
        redirectAttributes.addFlashAttribute("passwordModified", true);
        return "redirect:/mypage/setting";
    }

}
