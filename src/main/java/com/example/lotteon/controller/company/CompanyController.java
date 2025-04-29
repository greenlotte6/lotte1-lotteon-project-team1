package com.example.lotteon.controller.company;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompanyController {
    @GetMapping("/company")
    public String company() {
        return "/common/company/index";
    }

    @GetMapping("/company/culture")
    public String culture() {
        return "/common/company/culture";
    }
    @GetMapping("/company/blog")
    public String blog() {
        return "/common/company/blog";
    }
    @GetMapping("/company/recruit-list")
    public String recruit() {
        return "/common/company/recruit";
    }
    @GetMapping("/company/media-list")
    public String media() {
        return "/common/company/media";
    }





}
