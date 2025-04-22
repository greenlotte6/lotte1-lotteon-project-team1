package com.example.lotteon.service.user;

import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper mapper;

  public void userRegister(UserDTO userDTO) {
    log.info("userDTO: {}", userDTO);

    userDTO.setRole("member");
    String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
    userDTO.setPassword(encodedPassword);

    // 엔티티 변환
    User user = mapper.map(userDTO, User.class);

    // 저장
    userRepository.save(user);
  }


}
