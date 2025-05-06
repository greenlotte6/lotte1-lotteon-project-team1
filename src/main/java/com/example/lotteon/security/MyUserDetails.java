package com.example.lotteon.security;

import com.example.lotteon.entity.user.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
public class MyUserDetails implements UserDetails {

  private User user;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // 권한 목록 생성
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getId();
  }

  @Override
  public boolean isAccountNonExpired() {
    // 계정 만료 여부(true : 만료안됨, false : 만료됨)
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    // 계정 잠김 여부
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // 비밀번호 만료 여부
    return true;
  }

  @Override
  public boolean isEnabled() {
    // 계정 활성화 여부
    return true;
  }
}
