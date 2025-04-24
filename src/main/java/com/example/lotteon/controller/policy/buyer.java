package com.example.lotteon.controller.policy;

import org.springframework.web.bind.annotation.GetMapping;

public class buyer {

    @GetMapping("/policy/buyer")
    public String buyer() {

        return "/policy/buyer";
    }
}
