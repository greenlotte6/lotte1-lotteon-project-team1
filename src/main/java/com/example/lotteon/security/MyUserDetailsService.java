package com.example.lotteon.security;

import com.example.lotteon.entity.user.User;
import com.example.lotteon.repository.jpa.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("username: " + username);

    // 사용자 조회 - 입력받은 아이디 (비밀번호는 이전단계인 AuthenticationProvider 쪽에서 먼저 수행됨)
    Optional<User> optUser = userRepository.findById(username);

    if (!optUser.isPresent()) {
      return null;
    }

    // role이 "withdrawed"인 경우 로그인 차단
    User user = optUser.get();

    // role이 "withdrawed"인 경우 로그인 차단
    if ("withdrawed".equals(user.getRole())) {
      throw new UsernameNotFoundException("This account has been deactivated.");
    }

    // Security 사용자 인증객체 생성
    MyUserDetails myUserDetails = MyUserDetails.builder()
        .user(optUser.get())
        .build();

    // 리턴되는 myUserDetails는 Security ContextHolder에 저장됨
    return myUserDetails;
  }
}
