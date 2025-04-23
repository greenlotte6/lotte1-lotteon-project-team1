package com.example.lotteon.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        // 로그인 설정
        http.formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/") // 리다이렉트
                .failureUrl("/login?code=100")
                .usernameParameter("id")
                .passwordParameter("password"));

        // 로그아웃 설정
        http.logout(logout -> logout.logoutUrl("/logout")
                .invalidateHttpSession(true) // 세션 무효화
                .clearAuthentication(true) // 인증정보 객체 삭제
                .logoutSuccessUrl("/login"));

        // 인가설정
//        http.authorizeHttpRequests(authorize -> authorize
//                .requestMatchers("/").permitAll()
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
//                .requestMatchers("/staff/**").hasAnyRole("ADMIN", "MANAGER", "STAFF")
//                .requestMatchers("/article/**").authenticated()
//                .requestMatchers("/user/**").permitAll()
//                .anyRequest().permitAll());

        // 기타 보안 설정
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        // Security 암호화 인코더 설정
        return new BCryptPasswordEncoder();
    }
}
