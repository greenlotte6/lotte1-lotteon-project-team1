package com.example.lotteon.service.admin.point;

import com.example.lotteon.dto.point.PointDTO;
import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.dto.user.MemberIdDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.point.Point;
import com.example.lotteon.repository.jpa.admin.point.PointRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

  private final PointRepository repo;

  public Page<Point> findAll(Pageable pageable) {
    return repo.findAll(pageable);
  }

  public Page<Point> findByMemberId(String memberId, Pageable pageable) {
    return repo.findByMemberId(memberId, pageable);
  }

  public Page<Point> findByMemberName(String memberName, Pageable pageable) {
    return repo.findByMemberName(memberName, pageable);
  }

  public Page<Point> findByEmail(String email, Pageable pageable) {
    return repo.findByEmail(email, pageable);
  }

  public Page<Point> findByContact(String contact, Pageable pageable) {
    return repo.findByContact(contact, pageable);
  }

  public void deleteMultipleById(List<Integer> ids) {
    for (Integer id : ids) {
      repo.deleteById(id);
    }
  }


  //마이페이지에서 사용하는 서비스
  public Page<PointDTO> findByUserId(String userId, Pageable pageable) {
    Page<Point> pointPage = repo.findByMember_MemberId_User_Id(userId, pageable);

    return pointPage.map(point -> {
      // UserDTO 생성
      UserDTO userDTO = UserDTO.builder()
          .id(point.getMember().getMemberId().getUser().getId())
          .build();

      // MemberIdDTO 생성
      MemberIdDTO memberIdDTO = MemberIdDTO.builder()
          .user(userDTO)
          .build();

      // MemberDTO 생성
      MemberDTO memberDTO = MemberDTO.builder()
          .memberId(memberIdDTO)
          .name(point.getMember().getName())
          .build();

      // 최종 PointDTO 생성
      return PointDTO.builder()
          .id(point.getId())
          .amount(point.getAmount())
          .description(point.getDescription())
          .issuedDate(point.getIssuedDate())
          .total(point.getTotal())
          .memberId(memberDTO)
          .build();
    });
  }
}
