package com.example.lotteon.security;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SecurityConfig {

  private final LoginSuccessHandler loginSuccessHandler; // 추가

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {

    http.formLogin(login -> login
        .loginPage("/login")
        .defaultSuccessUrl("/")
        .failureUrl("/login?code=100")
        .usernameParameter("id")
        .passwordParameter("password")
        .successHandler(loginSuccessHandler) // ✅ 여기 추가
    );

    http.logout(logout -> logout
        .logoutUrl("/logout")
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .logoutSuccessUrl("/login")
    );

    // 인가설정
//        http.authorizeHttpRequests(authorize -> authorize
//                .requestMatchers("/").permitAll()
//                .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
//                .requestMatchers("/staff/**").hasAnyRole("ADMIN", "MANAGER", "STAFF")
//                .requestMatchers("/article/**").authenticated()
//                .requestMatchers("/user/**").permitAll()
//                .anyRequest().permitAll());

    http.authorizeHttpRequests(
        authorize -> authorize.requestMatchers("/seller/**").hasRole("SELLER")
            .requestMatchers("/admin/config/**", "/admin/seller/**", "/admin/member/**",
                "/admin/coupon/**", "/admin/cs/**")
            .hasRole("ADMIN")
            .requestMatchers("/admin", "/admin/sales/**", "/admin/product/**", "/admin/order/**",
                "/admin/delivery/**",
                "/admin/product/**")
            .hasAnyRole("ADMIN", "SELLER")
            .requestMatchers("/order/**").hasRole("MEMBER")
            .requestMatchers("/styles/**", "/js/**", "/images/**", "/static/**").permitAll()
            .anyRequest()
            .permitAll());

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
