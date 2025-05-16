package com.example.lotteon.service.mypage;

import com.example.lotteon.dto.order.MypageOrderWrapper;
import com.example.lotteon.dto.order.OrderItemDTO;
import com.example.lotteon.dto.seller.SellerDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.order.Order;
import com.example.lotteon.entity.order.OrderItem;
import com.example.lotteon.entity.order.OrderStatus;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.repository.UserRepository;
import com.example.lotteon.repository.order.OrderItemRepository;
import com.example.lotteon.repository.order.OrderRepository;
import com.example.lotteon.repository.seller.SellerRepository;
import com.example.lotteon.repository.user.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        }else {
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
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        // 2. role을 "withdrawed"로 변경
        user.setRole("withdrawed");

        // 3. 변경 사항을 저장
        userRepository.save(user);
    }


    public List<OrderItemDTO> getOrderDetail(String userId, String orderNumber) {
        List<OrderItem> entities = orderRepository.findWithProductInfoByOrderNumberAndUserId(orderNumber, userId);

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
}
