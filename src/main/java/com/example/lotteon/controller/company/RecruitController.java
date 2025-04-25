package com.example.lotteon.controller.company;


import com.example.lotteon.repository.recruit.RecruitRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class RecruitController {

    private final RecruitRepository recruitRepository;

    /** 채용 목록 */
    @GetMapping("/company/recruit-list")
    public String list(Model model) {
        model.addAttribute("posts", recruitRepository.findAll());
        return "common/company/recruit";   // → templates/common/company/recruit.html
    }
}