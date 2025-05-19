package com.example.lotteon.service.user;

import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.seller.Seller;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.exception.EntityAlreadyExistsException;
import com.example.lotteon.repository.jpa.UserRepository;
import com.example.lotteon.repository.jpa.seller.SellerRepository;
import com.example.lotteon.repository.jpa.user.MemberRepository;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper mapper;
  private final JavaMailSender mailSender;
  private final HttpServletRequest request;
  private final ModelMapper modelMapper;
  private final SellerRepository sellerRepository;


  public void register(UserDTO userDTO, String role) throws EntityAlreadyExistsException {
    log.info("Registry requested for userDTO: {}", userDTO);

    if (userRepository.existsById(userDTO.getId())) {
      throw new EntityAlreadyExistsException("User already exists");
    }

    userDTO.setRole(role);
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

    if (type.equals("id")) {
      count = userRepository.countById(value);
    } else if (type.equals("email")) {
      count = userRepository.countByEmail(value); // ✅ 중복 여부만 확인
    } else if (type.equals("contact")) {
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

  public UserDTO getUserInfo(String userId) {
    return userRepository.findById(userId)
        .map(user -> mapper.map(user, UserDTO.class))
        .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));
  }

  public Optional<String> findUserId(String name, String email) {
    // member에서 먼저 찾기
    Optional<Member> member = memberRepository.findByNameAndMemberIdUserEmail(name, email);

    if (member.isPresent()) {
      return Optional.of(member.get().getMemberId().getUser().getId());
    }

    // seller에서 찾기
    Optional<Seller> seller = sellerRepository.findByCeoAndSellerIdUserEmail(name, email);
    if (seller.isPresent()) {
      return Optional.of(seller.get().getSellerId().getUser().getId());
    }

    return Optional.empty();
  }

  public boolean existsByNameAndEmail(String name, String email) {
    // member 또는 seller 테이블에서 이름 + email.user.id 조합으로 조회
    Optional<Member> member = memberRepository.findByNameAndMemberIdUserEmail(name, email);
    if (member.isPresent()) {
      return true;
    }

    Optional<Seller> seller = sellerRepository.findByCeoAndSellerIdUserEmail(name, email);
    return seller.isPresent();
  }

  public Optional<User> findByIdAndEmail(String id, String email) {
    return userRepository.findByIdAndEmail(id, email);
  }

  public String sendTempPassword(User user) {
    String tempPassword = UUID.randomUUID().toString().substring(0, 10);
    String encodedPassword = passwordEncoder.encode(tempPassword);

    user.setPassword(encodedPassword);
    userRepository.save(user);

    // 메일 전송
    try {
      MimeMessage message = mailSender.createMimeMessage();
      message.setSubject("lotteOn 임시 비밀번호 안내");
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
      message.setContent(
          "<h2>임시 비밀번호는 <b>" + tempPassword + "</b> 입니다.</h2><p>로그인 후 비밀번호를 변경해주세요.</p>",
          "text/html;charset=UTF-8");
      message.setFrom(new InternetAddress(sender, "lotteOn"));
      mailSender.send(message);
    } catch (Exception e) {
      log.error("메일 전송 실패: {}", e.getMessage());
    }

    return tempPassword;
  }

}
