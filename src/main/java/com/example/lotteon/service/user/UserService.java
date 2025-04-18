package com.example.lotteon.service.user;

import com.example.lotteon.dto.UserDTO;
import com.example.lotteon.entity.User;
import com.example.lotteon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public void userRegister(UserDTO userDTO){
        log.info("userDTO: {}", userDTO);

        // 엔티티 변환
        User user = modelMapper.map(userDTO, User.class);

        // 저장
        userRepository.save(user);

    }


}
