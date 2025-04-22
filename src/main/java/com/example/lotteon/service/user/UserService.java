package com.example.lotteon.service.user;

import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.repository.UserRepository;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper mapper;
  private final JavaMailSender mailSender;
  private final HttpServletRequest request;

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

  // 유효성 검사
  public long checkUser(String type, String value) {

    long count = 0;

    if(type.equals("id")) {
      count = userRepository.countById(value);
    }else if(type.equals("email")) {
      count = userRepository.countByEmail(value);

      if(count == 0) {
        String code = sendEmailCode(value);

        // 인증코드 비교를 위해 세션 저장
        HttpSession session = request.getSession();
        session.setAttribute("Code", code);
      }
    }
    else if(type.equals("contact")) {
      count = userRepository.countByContact(value);
    }
    return count;
  }

  @Value("${spring.mail.username}")
  private String sender;
  public String sendEmailCode(String receiver) {

    // MimeMessage 생성
    MimeMessage message = mailSender.createMimeMessage();

    // 인증코드 생성
    int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
    log.info("code: {}", code);

    String subject = "lotteOn 인증코드 안내";
    String content = "<h1>lotteOn 인증코드는 " + code + "입니다.</h1>";

    try {
      // 메일 정보 설정
      message.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
      message.setSubject(subject);
      message.setContent(content, "text/html;charset=UTF-8");

      // 메일 발송
      mailSender.send(message);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return String.valueOf(code);
  }

}
