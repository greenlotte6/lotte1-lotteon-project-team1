package com.example.lotteon.controller.admin.recruit;

import com.example.lotteon.entity.recruit.EmploymentType;
import com.example.lotteon.entity.recruit.Recruit;
import com.example.lotteon.entity.recruit.RecruitCareer;
import com.example.lotteon.entity.recruit.RecruitDepartment;
import com.example.lotteon.service.recruit.RecruitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/recruit")
@RequiredArgsConstructor
public class RecruitAdminController {

    private final RecruitService recruitService;
    private final EmploymentType employmentType;
    private final RecruitCareer recruitCareer;
    private final RecruitDepartment recruitDepartment;


    //등록 폼
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("recruit", new Recruit());
        model.addAttribute("employmentType", employmentType);
        model.addAttribute("recruitCareer", recruitCareer);
        model.addAttribute("recruitDepartment", recruitDepartment);
        return "/admin/recruit/register";
    }
    // 목록 보기
    @GetMapping("/list")
    public String listRecruit(Model model) {
        model.addAttribute("recruits", recruitService.findAll());
        return "admin/recruit/list";
    }

    // 삭제 처리
    @PostMapping("/delete")
    public String deleteRecruits(@RequestParam List<Long> ids) {
        recruitService.deleteByIds(ids);
        return "redirect:/admin/recruit/list";
    }
}
