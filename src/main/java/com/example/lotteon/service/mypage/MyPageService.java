package com.example.lotteon.service.mypage;

import com.example.lotteon.dto.order.OrderItemDTO;
import com.example.lotteon.dto.order.ReturnDTO;
import com.example.lotteon.dto.seller.SellerDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.order.Order;
import com.example.lotteon.entity.order.OrderItem;
import com.example.lotteon.entity.order.OrderStatus;
import com.example.lotteon.entity.order.Return;
import com.example.lotteon.entity.order.ReturnReason;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.repository.jpa.UserRepository;
import com.example.lotteon.repository.jpa.order.OrderItemRepository;
import com.example.lotteon.repository.jpa.order.OrderRepository;
import com.example.lotteon.repository.jpa.order.ReturnRepository;
import com.example.lotteon.repository.jpa.seller.SellerRepository;
import com.example.lotteon.repository.jpa.user.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

  private final ModelMapper modelMapper;
  private final UserRepository userRepository;
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper mapper;
  private final JavaMailSender mailSender;
  private final HttpServletRequest request;
  private final OrderItemRepository orderItemRepository;
  private final OrderRepository orderRepository;
  private final SellerRepository sellerRepository;
  private final ReturnRepository returnRepository;


  @Transactional
  public void modifyUser(UserDTO userDTO) {

    String loginUserId = SecurityContextHolder.getContext().getAuthentication().getName();

    //pk
    Optional<User> optUser = userRepository.findById(loginUserId);
    if (optUser.isPresent()) {
      User user = optUser.get();  //기존 데이터 조회

      //새로운 값으로 업데이트
      user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
      user.setContact(userDTO.getContact());
      user.setEmail(userDTO.getEmail());
      user.setAddress(userDTO.getAddress());
      user.setAddressDetail(userDTO.getAddressDetail());
      user.setZip(userDTO.getZip());

      //저장
      userRepository.save(user);
    } else {
      throw new IllegalArgumentException("해당 회원이 존재하지 않습니다.");
    }
  }

  public boolean check(UserDTO userDTO) {

    return userRepository.findById(userDTO.getId())
        .map(user -> passwordEncoder.matches(userDTO.getPassword(), user.getPassword()))
        .orElse(false);
  }

  @Transactional
  public void deleteUser(String id) {
    // 1. 사용자 조회
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));

    // 2. role을 "withdrawed"로 변경
    user.setRole("withdrawed");

    // 3. 변경 사항을 저장
    userRepository.save(user);
  }


  public List<OrderItemDTO> getOrderDetail(String userId, String orderNumber) {
    List<OrderItem> entities = orderRepository.findWithProductInfoByOrderNumberAndUserId(
        orderNumber, userId);

    return entities.stream()
        .map(entity -> mapper.map(entity, OrderItemDTO.class))
        .collect(Collectors.toList());
  }


  public SellerDTO getSellerDetail(String businessNumber) {
    return sellerRepository.findBySellerId_BusinessNumber(businessNumber)
        .map(seller -> mapper.map(seller, SellerDTO.class))
        .orElse(null);
  }

  @Transactional
  public void confirmOrder(String orderNumber) {
    Order order = orderRepository.findByOrderNumber(orderNumber);

    if (order == null) {
      throw new IllegalArgumentException("해당 주문을 찾을 수 없습니다.");
    }

    // 상태를 "구매확정(6)"으로 변경
    order.setStatus(OrderStatus.builder().id(6).build());

    orderRepository.save(order); // 변경 사항 저장
  }


  //반품 신청 서비스
  @Transactional
  public int save(ReturnDTO returnDTO) {
    Order order = orderRepository.findByOrderNumber(returnDTO.getOrder_number());

    if (order == null) {
      throw new IllegalArgumentException("해당 주문을 찾을 수 없습니다.");
    }

    // 로그인한 사용자 정보 가져오기
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String userId = ((UserDetails) auth.getPrincipal()).getUsername();
    Member member = memberRepository.findOptionalByUserId(userId)
        .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

    // 📌 반품 사유 ID에 따라 주문 상태 분기 처리
    int reasonId = returnDTO.getReason_id();
    if (reasonId >= 1 && reasonId <= 4) {
      order.setStatus(OrderStatus.builder().id(9).build()); // 환불요청
    } else if (reasonId >= 5 && reasonId <= 8) {
      order.setStatus(OrderStatus.builder().id(11).build()); // 교환요청
    } else {
      throw new IllegalArgumentException("유효하지 않은 반품 사유 ID입니다.");
    }

    orderRepository.save(order); // 변경 사항 저장

    // 반품 엔티티 저장
    Return returnEntity = Return.builder()
        .order(order)
        .member(member)
        .returnReason(ReturnReason.builder().id(reasonId).build())
        .description(returnDTO.getDescription())
        .build();

    returnRepository.save(returnEntity);
    return returnEntity.getId();
  }
}
