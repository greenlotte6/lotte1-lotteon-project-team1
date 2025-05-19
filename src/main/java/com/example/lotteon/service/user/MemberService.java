package com.example.lotteon.service.user;

import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.dto.user.MemberIdDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.coupon.Coupon;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.entity.user.MemberId;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.repository.jpa.coupon.CouponRepository;
import com.example.lotteon.repository.jpa.user.MemberRepository;
import com.example.lotteon.service.coupon.CouponHistoryService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository repo;
  private final ModelMapper mapper;
  private final CouponHistoryService couponHistoryService; // 쿠폰 발급 서비스
  private final CouponRepository couponRepository;

  private Member toEntity(MemberDTO dto) {
    User user = mapper.map(dto.getMemberId().getUser(), User.class);
    MemberId compositeKey = MemberId.builder()
        .user(user)
        .build();
    return Member.builder()
        .memberId(compositeKey)
        .name(dto.getName())
        .gender(dto.getGender())
        .description(dto.getDescription())
        .level(dto.getLevel())
        .status(dto.getStatus())
        .build();
  }

  public Long countNewMembersOf(LocalDate date) {
    return repo.countNewMembersOf(date);
  }

  public void memberRegister(MemberDTO memberDTO) {
    memberDTO.setRecentLoginDate(LocalDate.now());

    Member member = mapper.map(memberDTO, Member.class);
    repo.save(member);

    // 회원가입 후 쿠폰 발급 (쿠폰이 없으면 실패 처리)
    try {
      // 101번 쿠폰(회원가입 관련 쿠폰) 조회
      Coupon coupon = couponRepository.findById(101)
          .orElseThrow(() -> new IllegalStateException("회원가입 쿠폰을 찾을 수 없습니다."));

      // 쿠폰 발급
      couponHistoryService.couponHistoryRegister(member, coupon);
    } catch (Exception e) {   // 쿠폰 발급이 실패하더라도 회원가입은 진행
      log.warn("회원가입 쿠폰 발급 실패: {}", e.getMessage());
    }
  }

  public MemberDTO getById(String id) {
    Member member = repo.findById(id);
    UserDTO userDTO = mapper.map(member.getMemberId().getUser(), UserDTO.class);
    MemberIdDTO memberIdDTO = new MemberIdDTO(userDTO);
    return MemberDTO.builder()
        .memberId(memberIdDTO)
        .name(member.getName())
        .gender(member.getGender())
        .recentLoginDate(member.getRecentLoginDate())
        .description(member.getDescription())
        .status(member.getStatus())
        .level(member.getLevel())
        .build();
  }

  public Page<Member> getAll(Pageable pageable) {
    return repo.findAll(pageable);
  }

  public Page<Member> getAllById(Pageable pageable, String id) {
    return repo.findAllById(pageable, id);
  }

  public Page<Member> getAllByName(Pageable pageable, String email) {
    return repo.findAllByName(pageable, email);
  }

  public Page<Member> getAllByEmail(Pageable pageable, String email) {
    return repo.findAllByEmail(pageable, email);
  }

  public Page<Member> getAllByContact(Pageable pageable, String contact) {
    return repo.findAllByContact(pageable, contact);
  }

  public void updateLevel(List<MemberDTO> members) {
    for (MemberDTO memberDTO : members) {
      Member member = toEntity(memberDTO);
      repo.updateLevel(member);
    }
  }

  public void updateStatus(MemberDTO memberDTO) {
    Member member = toEntity(memberDTO);
    repo.updateStatus(member);
  }

  public void edit(MemberDTO memberDTO) {
    Member member = toEntity(memberDTO);
    repo.updateInfo(member);
  }

  // 쿠폰 받기 눌렸을때, 마이페이지 설정에 필요한 user_id 가져오는 메서드
  public MemberDTO findByUserId(String userId) {
    Member member = repo.findByUserId(userId);
    return mapper.map(member, MemberDTO.class);
  }

  // 엔티티 반환 메서드
  public Member getMemberEntityByUserId(String userId) {
    Member member = repo.findByUserId(userId);
    if (member == null) {
      throw new EntityNotFoundException("회원을 찾을 수 없습니다.");
    }
    return member;
  }

}
