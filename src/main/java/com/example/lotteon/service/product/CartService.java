package com.example.lotteon.service.product;

import com.example.lotteon.dto.product.CartDTO;
import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.dto.user.MemberIdDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.product.Cart;
import com.example.lotteon.repository.jpa.cart.CartRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

  private final CartRepository repo;
  private final ModelMapper mapper;

  public void addAll(String memberId, List<CartDTO> cartItems) {
    List<Cart> entities = cartItems.stream().map((dto) -> {
      UserDTO user = UserDTO.builder()
          .id(memberId)
          .build();
      MemberIdDTO memberIdDTO = MemberIdDTO.builder().user(user).build();
      MemberDTO member = MemberDTO.builder()
          .memberId(memberIdDTO)
          .build();
      dto.setMember(member);
      dto.setRegisterDate(LocalDate.now());
      return mapper.map(dto, Cart.class);
    }).toList();
    repo.saveAll(entities);
    repo.flush();
  }

  public void add(String memberId, CartDTO cartItem) {
    UserDTO user = UserDTO.builder()
        .id(memberId)
        .build();
    MemberIdDTO memberIdDTO = MemberIdDTO.builder().user(user).build();
    MemberDTO member = MemberDTO.builder()
        .memberId(memberIdDTO)
        .build();
    cartItem.setMember(member);
    cartItem.setRegisterDate(LocalDate.now());
    Cart cart = mapper.map(cartItem, Cart.class);
    repo.save(cart);
  }

  public List<CartDTO> listForCurrentMember(String memberId) {
    List<Cart> entities = repo.findAllByMemberId(memberId);
    return entities.stream().map((entity) -> {
      return mapper.map(entity, CartDTO.class);
    }).toList();
  }

  public CartDTO get(String id) {
    return null;
  }

  public void deleteByMemberId(String memberId) {
    repo.deleteByMemberId(memberId);
  }
}

