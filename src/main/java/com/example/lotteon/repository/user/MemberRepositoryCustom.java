package com.example.lotteon.repository.user;

import com.example.lotteon.entity.user.Member;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepositoryCustom {

  /**
   * 금일의 신규 유입 회원수를 조회하는 메서드
   *
   * @return 금일 새롭게 회원가입한 회원들의 수
   */
  Long countNewMembersOf(LocalDate date);

  Page<Member> findAll(Pageable pageable);
}
