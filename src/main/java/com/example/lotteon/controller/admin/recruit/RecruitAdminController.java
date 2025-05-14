package com.example.lotteon.controller.admin.recruit;

import com.example.lotteon.entity.recruit.Recruit;
import com.example.lotteon.service.recruit.RecruitService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/recruit")
@RequiredArgsConstructor
public class RecruitAdminController {

  private final RecruitService recruitService;

  // 등록 폼 요청
  @GetMapping("/register")
  public String showRegisterForm(Model model) {
    // 여기서 등록 폼을 보여주는 경우 list로 리턴할지, register 폼을 따로 만들 것인지에 따라 경로가 달라집니다.
    return "admin/cs/recruit/list"; // 혹은 register.html이 있다면 "admin/cs/recruit/register"
  }

  // 목록 보기
  @GetMapping("/list")
  public String listRecruit(Model model) {
    List<Recruit> recruits = recruitService.getAllRecruits();
    model.addAttribute("recruits", recruits);
    return "admin/cs/recruit/list";
  }

  // 삭제 처리
  @PostMapping("/delete")
  public String deleteRecruits(@RequestParam List<Long> ids) {
    recruitService.deleteByIds(ids);
    return "redirect:admin/recruit/list";
  }
}