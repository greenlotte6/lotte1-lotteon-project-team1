package com.example.lotteon.controller.company;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompanyController {
    @GetMapping("/company")
    public String company() {
        return "/common/company/index";
    }
}
