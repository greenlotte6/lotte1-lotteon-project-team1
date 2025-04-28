package com.example.lotteon.service.user;

import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.entity.user.UserCompositeKey;
import com.example.lotteon.repository.user.MemberRepository;
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

  private Member toEntity(MemberDTO dto) {
    User user = mapper.map(dto.getMemberId().getUser(), User.class);
    UserCompositeKey compositeKey = UserCompositeKey.builder()
        .user(user)
        .build();
    return Member.builder()
        .userCompositeKey(compositeKey)
        .level(dto.getLevel())
        .build();
  }

  public Long countNewMembersOf(LocalDate date) {
    return repo.countNewMembersOf(date);
  }

  public void memberRegister(MemberDTO memberDTO) {
    memberDTO.setRecentLoginDate(LocalDate.now());

    Member member = mapper.map(memberDTO, Member.class);
    repo.save(member);
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

}
