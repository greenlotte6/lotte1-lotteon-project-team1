package com.example.lotteon.controller.mypage;

import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.repository.UserRepository;
import com.example.lotteon.repository.user.MemberRepository;
import com.example.lotteon.service.mypage.MyPageService;
import com.example.lotteon.service.user.MemberService;
import com.example.lotteon.service.user.UserService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
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
    @PostMapping("/mypage/setting/delete")
    public String delete(String id) {
        //서비스 호출
        myPageService.deleteUser(id);

        return "redirect:/mypage/wholeorder";
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
