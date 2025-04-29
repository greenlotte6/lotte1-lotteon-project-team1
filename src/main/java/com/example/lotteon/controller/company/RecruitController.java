package com.example.lotteon.controller.company;

import com.example.lotteon.service.recruit.RecruitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RecruitController {

    private final RecruitService recruitService;

    @GetMapping("/company/recruit/list")
    public String recruitList(Model model) {
        model.addAttribute("posts", recruitService.getAllRecruits());
        return "/common/company/recruit";
    }
}
