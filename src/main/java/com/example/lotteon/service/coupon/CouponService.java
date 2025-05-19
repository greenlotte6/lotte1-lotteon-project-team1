package com.example.lotteon.service.coupon;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.dto.PageResponseDTO;
import com.example.lotteon.dto.coupon.CouponDTO;
import com.example.lotteon.dto.coupon.Coupon_BenefitDTO;
import com.example.lotteon.dto.coupon.Coupon_TypeDTO;
import com.example.lotteon.entity.coupon.Coupon;
import com.example.lotteon.entity.coupon.Coupon_Benefit;
import com.example.lotteon.entity.coupon.Coupon_Type;
import com.example.lotteon.repository.jpa.coupon.CouponRepository;
import com.querydsl.core.Tuple;
import java.time.LocalDate;
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
public class CouponService {

  private final CouponRepository couponRepository;
  private final ModelMapper modelMapper;

  public int couponRegister(CouponDTO couponDTO) {

    Coupon coupon = Coupon.builder()
        .coupon_type(Coupon_Type.builder().id(couponDTO.getType_id()).build())
        .name(couponDTO.getName())
        .coupon_benefit(Coupon_Benefit.builder().id(couponDTO.getBenefit_id()).build())
        .from(couponDTO.getFrom())
        .to(couponDTO.getTo())
        .seller_id(couponDTO.getSeller_id())
        .issued_amount(couponDTO.getIssued_amount())
        .used_amount(couponDTO.getUsed_amount())
        .status("issued")
        .description(couponDTO.getDescription())
        .build();

    //JPA 저장
    Coupon savedCoupon = couponRepository.save(coupon);

    //저장한 글번호 반환
    return savedCoupon.getId();

  }

  public PageResponseDTO findAll(PageRequestDTO pageRequestDTO, int id) {
    Pageable pageable = pageRequestDTO.getPageable("id");
    String name = pageRequestDTO.getName();
    String seller_id = pageRequestDTO.getSeller_id();

    Page<Tuple> pageCoupon = couponRepository.selectAllForList(pageable, id, name, seller_id);

    LocalDateTime now = LocalDateTime.now();

    List<CouponDTO> couponDTOList = pageCoupon.getContent().stream().map(tuple -> {
      Coupon coupon = tuple.get(0, Coupon.class);

      // 날짜 비교하여 상태 자동 변경
      if (coupon.getTo() != null) {
        // 만약 coupon.getTo()가 String이라면 LocalDateTime으로 변환
        LocalDate couponToDate = LocalDate.parse(coupon.getTo(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (couponToDate.isBefore(now.toLocalDate()) && coupon.getStatus().equals("issued")) {
          coupon.setStatus("used");
          couponRepository.save(coupon);
        }
      }

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      String formattedIssuedDate =
          coupon.getIssued_date() != null ? coupon.getIssued_date().format(formatter) : null;
      Coupon_BenefitDTO benefitDTO = Coupon_BenefitDTO.builder()
          .id(coupon.getCoupon_benefit().getId())
          .benefit(coupon.getCoupon_benefit().getBenefit())
          .build();
      Coupon_TypeDTO typeDTO = Coupon_TypeDTO.builder()
          .id(coupon.getCoupon_type().getId())
          .name(coupon.getCoupon_type().getName())
          .build();
      CouponDTO couponDTO = CouponDTO.builder()
          .id(coupon.getId())
          .type_id(coupon.getCoupon_type().getId())
          .name(coupon.getName())
          .benefit_id(coupon.getCoupon_benefit().getId())
          .from(coupon.getFrom())
          .to(coupon.getTo())
          .seller_id(coupon.getSeller_id())
          .issued_amount(coupon.getIssued_amount())
          .used_amount(coupon.getUsed_amount())
          .status(coupon.getStatus())
          .description(coupon.getDescription())
          .issued_date(formattedIssuedDate) // LocalDateTime을 String으로 변환하여 설정
          .coupon_benefit(benefitDTO)
          .coupon_type(typeDTO)
          .build();

      return couponDTO;
    }).toList();

    int total = (int) pageCoupon.getTotalElements();

    return PageResponseDTO.<CouponDTO>builder()
        .pageRequestDTO(pageRequestDTO)
        .dtoList(couponDTOList)
        .total(total)
        .build();
  }


  @Transactional
  public boolean updateCouponStatus(int couponId) {
    Coupon coupon = couponRepository.findById(couponId)
        .orElseThrow(() -> new RuntimeException("쿠폰을 찾을 수 없습니다."));

    if ("issued".equals(coupon.getStatus())) {
      coupon.setStatus("used");
      couponRepository.save(coupon);
      return true;
    }

    return false;
  }


  public CouponDTO findById(int id) {
    return couponRepository.findById(id)
        .map(coupon -> {
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
          String formattedIssuedDate =
              coupon.getIssued_date() != null ? coupon.getIssued_date().format(formatter) : null;
          Coupon_BenefitDTO benefitDTO = Coupon_BenefitDTO.builder()
              .id(coupon.getCoupon_benefit().getId())
              .benefit(coupon.getCoupon_benefit().getBenefit())
              .build();
          Coupon_TypeDTO typeDTO = Coupon_TypeDTO.builder()
              .id(coupon.getCoupon_type().getId())
              .name(coupon.getCoupon_type().getName())
              .build();
          return CouponDTO.builder()
              .id(coupon.getId())
              .type_id(coupon.getCoupon_type().getId())
              .name(coupon.getName())
              .benefit_id(coupon.getCoupon_benefit().getId())
              .from(coupon.getFrom())
              .to(coupon.getTo())
              .seller_id(coupon.getSeller_id())
              .issued_amount(coupon.getIssued_amount())
              .used_amount(coupon.getUsed_amount())
              .status(coupon.getStatus())
              .description(coupon.getDescription())
              .issued_date(formattedIssuedDate) // LocalDateTime을 String으로 변환하여 설정
              .coupon_benefit(benefitDTO)
              .coupon_type(typeDTO)
              .build();
        })
        .orElseThrow(() -> new RuntimeException("쿠폰을 찾을 수 없습니다."));
  }

  public PageResponseDTO searchAll(PageRequestDTO pageRequestDTO, int id) {
    Pageable pageable = pageRequestDTO.getPageable("id");
    String name = pageRequestDTO.getName();
    String seller_id = pageRequestDTO.getSeller_id();

    Page<Tuple> pageCoupon = couponRepository.selectAllForSearch(pageRequestDTO, pageable, id, name,
        seller_id);

    LocalDateTime now = LocalDateTime.now();

    List<CouponDTO> couponDTOList = pageCoupon.getContent().stream().map(tuple -> {
      Coupon coupon = tuple.get(0, Coupon.class);

      // 날짜 비교하여 상태 자동 변경
      if (coupon.getTo() != null) {
        // 만약 coupon.getTo()가 String이라면 LocalDateTime으로 변환
        LocalDate couponToDate = LocalDate.parse(coupon.getTo(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (couponToDate.isBefore(now.toLocalDate()) && coupon.getStatus().equals("issued")) {
          coupon.setStatus("used");
          couponRepository.save(coupon);
        }
      }

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      String formattedIssuedDate =
          coupon.getIssued_date() != null ? coupon.getIssued_date().format(formatter) : null;
      Coupon_BenefitDTO benefitDTO = Coupon_BenefitDTO.builder()
          .id(coupon.getCoupon_benefit().getId())
          .benefit(coupon.getCoupon_benefit().getBenefit())
          .build();
      Coupon_TypeDTO typeDTO = Coupon_TypeDTO.builder()
          .id(coupon.getCoupon_type().getId())
          .name(coupon.getCoupon_type().getName())
          .build();
      CouponDTO couponDTO = CouponDTO.builder()
          .id(coupon.getId())
          .type_id(coupon.getCoupon_type().getId())
          .name(coupon.getName())
          .benefit_id(coupon.getCoupon_benefit().getId())
          .from(coupon.getFrom())
          .to(coupon.getTo())
          .seller_id(coupon.getSeller_id())
          .issued_amount(coupon.getIssued_amount())
          .used_amount(coupon.getUsed_amount())
          .status(coupon.getStatus())
          .description(coupon.getDescription())
          .issued_date(formattedIssuedDate) // LocalDateTime을 String으로 변환하여 설정
          .coupon_benefit(benefitDTO)
          .coupon_type(typeDTO)
          .build();

      return couponDTO;
    }).toList();

    int total = (int) pageCoupon.getTotalElements();

    return PageResponseDTO.<CouponDTO>builder()
        .pageRequestDTO(pageRequestDTO)
        .dtoList(couponDTOList)
        .total(total)
        .build();
  }
}
