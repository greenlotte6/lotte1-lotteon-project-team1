package com.example.lotteon.service.user;

import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.repository.user.MemberRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository repo;
  private final ModelMapper mapper;

  public Long countNewMembersOf(LocalDate date) {
    return repo.countNewMembersOf(date);
  }

  public void memberRegister(MemberDTO memberDTO) {
    memberDTO.setRecentLoginDate(LocalDate.now());

    Member member = mapper.map(memberDTO, Member.class);
    repo.save(member);
  }

}
