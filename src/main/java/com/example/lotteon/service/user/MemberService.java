package com.example.lotteon.service.user;

import com.example.lotteon.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository repo;

  public Long countNewMembers() {
    return repo.countNewMembers();
  }
}
