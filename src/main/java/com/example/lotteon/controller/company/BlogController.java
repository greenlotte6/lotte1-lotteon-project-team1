package com.example.lotteon.controller.company;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {
    @GetMapping("/company/blog/article1")
    public String article1() {
        return "/common/company/articles/article1";
    }

    @GetMapping("/company/blog/article2")
    public String article2() {
        return "/common/company/articles/article2";
    }

    @GetMapping("/company/blog/article3")
    public String article3() {
        return "/common/company/articles/article3";
    }

    @GetMapping("/company/blog/article4")
    public String article4() {
        return "/common/company/articles/article4";
    }

    @GetMapping("/company/blog/article5")
    public String article5() {
        return "/common/company/articles/article5";
    }

}
