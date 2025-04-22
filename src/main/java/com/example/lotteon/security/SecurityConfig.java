package com.example.lotteon.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    // 로그인 설정
    http.formLogin(login -> login
            .loginPage("/user/login")
            .defaultSuccessUrl("/")
            .failureUrl("/user/login?code=100")
            .usernameParameter("id")
            .passwordParameter("password"));

    // 로그아웃 설정
    http.logout(logout -> logout
            .logoutUrl("/user/logout")
            .invalidateHttpSession(true)
            .logoutSuccessUrl("/user/login?code=101"));


      /*
            인가 설정
             - MyUserDetails 권한 목록 생성하는 메서드(getAuthorities)에서 접두어로 ROLE_ 입력해야 hasRole, hasAnyRole 권한 처리됨
             - Spring Security는 기본적으로 인가 페이지 대해 login 페이지로 redirect 수행
        */
//    http.authorizeHttpRequests(authorize -> authorize
//            .requestMatchers("/").permitAll()
//            .requestMatchers("/admin/**").hasRole("ADMIN")
//            .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
//            .requestMatchers("/staff/**").hasAnyRole("ADMIN", "MANAGER", "STAFF")
//            .requestMatchers("/article/**").authenticated()
//            .requestMatchers("/user/**").permitAll()
//            .anyRequest().permitAll());

    // 기타 보안 설정
    http.csrf(AbstractHttpConfigurer::disable);

    return http.build();

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    // Security 암호화 인코더 설정
    return new BCryptPasswordEncoder();
  }

}
