package com.example.lotteon.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/general")
    public String general() {
        return "/user/general";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @GetMapping("/seller")
    public String seller() {
        return "/user/seller";
    }

    @GetMapping("/sort")
    public String sort() {
        return "/user/sort";
    }

    @GetMapping("/terms")
    public String terms() {
        return "/user/terms";
    }

    @GetMapping("/tax")
    public String tax() {
        return "/user/tax";
    }
}
