package com.example.lotteon.service.coupon;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.dto.PageResponseDTO;
import com.example.lotteon.dto.coupon.CouponDTO;
import com.example.lotteon.dto.coupon.Coupon_BenefitDTO;
import com.example.lotteon.dto.coupon.Coupon_HistoryDTO;
import com.example.lotteon.dto.coupon.Coupon_TypeDTO;
import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.dto.user.MemberIdDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.coupon.Coupon;
import com.example.lotteon.entity.coupon.Coupon_History;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.repository.jpa.coupon.CouponHistoryRepository;
import com.example.lotteon.repository.jpa.coupon.CouponRepository;
import com.querydsl.core.Tuple;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponHistoryService {

  private final CouponHistoryRepository couponHistoryRepository;
  private final CouponRepository couponRepository;
  private final ModelMapper modelMapper;

  public int couponHistoryRegister(Member member, Coupon coupon) {
    // 이미 쿠폰이 지급된 이력이 있는지 확인
    boolean couponAlreadyGiven = couponHistoryRepository.existsByMemberAndCoupon(member, coupon);
    if (couponAlreadyGiven) {
      throw new IllegalStateException("이미 쿠폰을 받으셨습니다.");
    }

    // 쿠폰 발급 내역 생성
    Coupon_History couponHistory = Coupon_History.builder()
        .coupon(coupon)
        .member(member)
        .status("unused")
        .build();

    // 저장
    Coupon_History savedCouponHistory = couponHistoryRepository.save(couponHistory);
    return savedCouponHistory.getId();
  }

  public PageResponseDTO findAll(PageRequestDTO pageRequestDTO, int id) {
    Pageable pageable = pageRequestDTO.getPageable("id");

    // couponHistoryRepository에서 조회
    Page<Tuple> pageCouponHistory = couponHistoryRepository.selectAllForList(pageable, id);

    List<Coupon_HistoryDTO> couponHistoryDTOList = pageCouponHistory.getContent().stream()
        .map(tuple -> {
          Coupon_History couponHistory = tuple.get(0, Coupon_History.class);

          UserDTO userDTO = UserDTO.builder()
              .id(couponHistory.getMember().getMemberId().getUser().getId())
              .build();

          MemberIdDTO memberIdDTO = MemberIdDTO.builder()
              .user(userDTO)
              .build();

          MemberDTO memberDTO = MemberDTO.builder()
              .memberId(memberIdDTO)
              .build();

          Coupon_BenefitDTO benefitDTO = Coupon_BenefitDTO.builder()
              .id(couponHistory.getCoupon().getCoupon_benefit().getId())
              .benefit(couponHistory.getCoupon().getCoupon_benefit().getBenefit())
              .build();

          Coupon_TypeDTO typeDTO = Coupon_TypeDTO.builder()
              .id(couponHistory.getCoupon().getCoupon_type().getId())
              .name(couponHistory.getCoupon().getCoupon_type().getName())
              .build();

          CouponDTO couponDTO = CouponDTO.builder()
              .id(couponHistory.getCoupon().getId())
              .type_id(couponHistory.getCoupon().getCoupon_type().getId())
              .name(couponHistory.getCoupon().getName())
              .coupon_benefit(benefitDTO)
              .coupon_type(typeDTO)
              .build();

          Coupon_HistoryDTO couponHistoryDTO = Coupon_HistoryDTO.builder()
              .id(couponHistory.getId())
              .coupon_id(couponHistory.getCoupon().getId())
              .user_id(couponHistory.getMember().getMemberId().getUser().getId())
              .status(couponHistory.getStatus())
              .used_date(couponHistory.getUsed_date())
              .coupon(couponDTO)
              .member(memberDTO)
              .build();

          return couponHistoryDTO;
        }).toList();

    int total = (int) pageCouponHistory.getTotalElements();

    return PageResponseDTO.<Coupon_HistoryDTO>builder()
        .pageRequestDTO(pageRequestDTO)
        .dtoList(couponHistoryDTOList)
        .total(total)
        .build();
  }


  public PageResponseDTO searchAll(PageRequestDTO pageRequestDTO, int id) {

    Pageable pageable = pageRequestDTO.getPageable("id");
    String name = pageRequestDTO.getName();
    int coupon_id = pageRequestDTO.getCoupon_id();
    String user_id = pageRequestDTO.getUser_id();

    // couponHistoryRepository에서 조회
    Page<Tuple> pageCouponHistory = couponHistoryRepository.selectAllForSearch(pageRequestDTO,
        pageable, id, name, coupon_id, user_id);

    List<Coupon_HistoryDTO> couponHistoryDTOList = pageCouponHistory.getContent().stream()
        .map(tuple -> {
          Coupon_History couponHistory = tuple.get(0, Coupon_History.class);

          UserDTO userDTO = UserDTO.builder()
              .id(couponHistory.getMember().getMemberId().getUser().getId())
              .build();

          MemberIdDTO memberIdDTO = MemberIdDTO.builder()
              .user(userDTO)
              .build();

          MemberDTO memberDTO = MemberDTO.builder()
              .memberId(memberIdDTO)
              .build();

          Coupon_BenefitDTO benefitDTO = Coupon_BenefitDTO.builder()
              .id(couponHistory.getCoupon().getCoupon_benefit().getId())
              .benefit(couponHistory.getCoupon().getCoupon_benefit().getBenefit())
              .build();

          Coupon_TypeDTO typeDTO = Coupon_TypeDTO.builder()
              .id(couponHistory.getCoupon().getCoupon_type().getId())
              .name(couponHistory.getCoupon().getCoupon_type().getName())
              .build();

          CouponDTO couponDTO = CouponDTO.builder()
              .id(couponHistory.getCoupon().getId())
              .type_id(couponHistory.getCoupon().getCoupon_type().getId())
              .name(couponHistory.getCoupon().getName())
              .coupon_benefit(benefitDTO)
              .coupon_type(typeDTO)
              .build();

          Coupon_HistoryDTO couponHistoryDTO = Coupon_HistoryDTO.builder()
              .id(couponHistory.getId())
              .coupon_id(couponHistory.getCoupon().getId())
              .user_id(couponHistory.getMember().getMemberId().getUser().getId())
              .status(couponHistory.getStatus())
              .used_date(couponHistory.getUsed_date())
              .coupon(couponDTO)
              .member(memberDTO)
              .build();

          return couponHistoryDTO;
        }).toList();

    int total = (int) pageCouponHistory.getTotalElements();

    return PageResponseDTO.<Coupon_HistoryDTO>builder()
        .pageRequestDTO(pageRequestDTO)
        .dtoList(couponHistoryDTOList)
        .total(total)
        .build();

  }

  public Coupon_HistoryDTO findById(int id) {
    return couponHistoryRepository.findById(id)
        .map(couponHistory -> {
          UserDTO userDTO = UserDTO.builder()
              .id(couponHistory.getMember().getMemberId().getUser().getId())
              .build();

          MemberIdDTO memberIdDTO = MemberIdDTO.builder()
              .user(userDTO)
              .build();

          MemberDTO memberDTO = MemberDTO.builder()
              .memberId(memberIdDTO)
              .build();

          Coupon_BenefitDTO benefitDTO = Coupon_BenefitDTO.builder()
              .id(couponHistory.getCoupon().getCoupon_benefit().getId())
              .benefit(couponHistory.getCoupon().getCoupon_benefit().getBenefit())
              .build();

          Coupon_TypeDTO typeDTO = Coupon_TypeDTO.builder()
              .id(couponHistory.getCoupon().getCoupon_type().getId())
              .name(couponHistory.getCoupon().getCoupon_type().getName())
              .build();

          CouponDTO couponDTO = CouponDTO.builder()
              .id(couponHistory.getCoupon().getId())
              .type_id(couponHistory.getCoupon().getCoupon_type().getId())
              .name(couponHistory.getCoupon().getName())
              .coupon_benefit(benefitDTO)
              .coupon_type(typeDTO)
              .from(couponHistory.getCoupon().getFrom())
              .to(couponHistory.getCoupon().getTo())
              .seller_id(couponHistory.getCoupon().getSeller_id())
              .description(couponHistory.getCoupon().getDescription())
              .build();
          return Coupon_HistoryDTO.builder()
              .id(couponHistory.getId())
              .coupon_id(couponHistory.getCoupon().getId())
              .user_id(couponHistory.getMember().getMemberId().getUser().getId())
              .status(couponHistory.getStatus())
              .used_date(couponHistory.getUsed_date())
              .coupon(couponDTO)
              .member(memberDTO)
              .build();
        })
        .orElseThrow(() -> new RuntimeException("쿠폰을 찾을 수 없습니다."));
  }

  @Transactional
  public void markAsUsed(int couponHistoryId) {
    Coupon_History history = couponHistoryRepository.findById(couponHistoryId)
        .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰 내역이 존재하지 않습니다."));

    if (!"unused".equals(history.getStatus())) {
      throw new IllegalStateException("이미 사용된 쿠폰입니다.");
    }

    // 상태 변경
    history.setStatus("used");

    // 현재 날짜 및 시간 문자열로 저장 (형식: yyyy-MM-dd HH:mm:ss)
    String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    history.setUsed_date(now);

    // save 생략 가능하지만 명시적으로 작성 가능
    couponHistoryRepository.save(history);
  }

  //public List<Coupon_HistoryDTO> findByUserId(String userId) {
  public Page<Coupon_HistoryDTO> findByUserId(String userId, Pageable pageable) {

    //List<Coupon_History> couponHistoryList = couponHistoryRepository.findByMember_MemberId_User_Id(userId);

    Page<Coupon_History> couponHistoryPage = couponHistoryRepository.findByMember_MemberId_User_Id(
        userId, pageable);

    //return couponHistoryList.stream().map(couponHistory -> {
    return couponHistoryPage.map(couponHistory -> {
      Coupon_BenefitDTO benefitDTO = Coupon_BenefitDTO.builder()
          .id(couponHistory.getCoupon().getCoupon_benefit().getId())
          .benefit(couponHistory.getCoupon().getCoupon_benefit().getBenefit())
          .build();

      Coupon_TypeDTO typeDTO = Coupon_TypeDTO.builder()
          .id(couponHistory.getCoupon().getCoupon_type().getId())
          .name(couponHistory.getCoupon().getCoupon_type().getName())
          .build();

      CouponDTO couponDTO = CouponDTO.builder()
          .id(couponHistory.getCoupon().getId())
          .type_id(couponHistory.getCoupon().getCoupon_type().getId())
          .name(couponHistory.getCoupon().getName())
          .to(couponHistory.getCoupon().getTo())
          .description(couponHistory.getCoupon().getDescription())
          .coupon_benefit(benefitDTO)
          .coupon_type(typeDTO)
          .build();

      UserDTO userDTO = UserDTO.builder()
          .id(couponHistory.getMember().getMemberId().getUser().getId())
          .build();

      MemberIdDTO memberIdDTO = MemberIdDTO.builder()
          .user(userDTO)
          .build();

      MemberDTO memberDTO = MemberDTO.builder()
          .memberId(memberIdDTO)
          .build();

      return Coupon_HistoryDTO.builder()
          .id(couponHistory.getId())
          .coupon_id(couponHistory.getCoupon().getId())
          .user_id(userDTO.getId())
          .status(couponHistory.getStatus())
          .used_date(couponHistory.getUsed_date())
          .coupon(couponDTO)
          .member(memberDTO)
          .build();
    });
  }
}
