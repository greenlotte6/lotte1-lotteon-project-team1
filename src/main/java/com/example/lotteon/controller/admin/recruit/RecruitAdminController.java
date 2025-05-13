package com.example.lotteon.controller.admin.recruit;

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


  //등록 폼
  @PostMapping("/register")
  public String showRegisterForm(Model model) {
    return "/admin/cs/recruit/list";
  }

  // 목록 보기
  @GetMapping("/list")
  public String listRecruit(Model model) {
    model.addAttribute("recruits", recruitService.findAll());
    return "/admin/cs/recruit/list";
  }

  // 삭제 처리
  @PostMapping("/delete")
  public String deleteRecruits(@RequestParam List<Long> ids) {
    recruitService.deleteByIds(ids);
    return "redirect:/admin/recruit/list";
  }
}
