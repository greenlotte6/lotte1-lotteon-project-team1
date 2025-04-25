package com.example.lotteon.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginUserService {

    public String getLoginUser() {
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUser.getUsername();
    }
}
