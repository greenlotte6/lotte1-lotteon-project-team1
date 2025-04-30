package com.example.lotteon.controller.company;

import com.example.lotteon.entity.recruit.Recruit;
import com.example.lotteon.service.recruit.RecruitService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Slf4j
@Controller
@RequiredArgsConstructor
public class RecruitController {

  private final RecruitService recruitService;

  /**
   * 채용 목록
   */
  @GetMapping("/company/recruit")
  public String list(Model model) {
    List<Recruit> posts = recruitService.getAll();
    log.info("posts : " + posts);

    // 모델 참조
    model.addAttribute("posts", posts);

    return "/common/company/recruit";   // → templates/common/company/recruit.html
  }

  @GetMapping("/recruit/{id}")
  public String viewRecruitDetail(@PathVariable Long id, Model model) {
    Recruit recruit = recruitService.getRecruitById(id); // 또는 DAOUDA 조회
    model.addAttribute("recruit", recruit);
    return "/common/company/recruit_detail"; // 템플릿 경로
  }

  @GetMapping("/company/recruit/list")
  public String recruitList(Model model) {
    model.addAttribute("posts", recruitService.getAllRecruits());
    return "/common/company/recruit";
  }





}
