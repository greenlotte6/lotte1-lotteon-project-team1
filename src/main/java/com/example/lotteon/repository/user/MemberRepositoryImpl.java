package com.example.lotteon.repository.user;

import com.example.lotteon.entity.QUser;
import com.example.lotteon.entity.user.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

  private final JPAQueryFactory query;
  private QMember member = QMember.member;
  private QUser user = QUser.user;

  @Override
  public Long countNewMembers() {
    LocalDateTime now = LocalDateTime.now();
    return query.select(member.count())
        .join(user)
        .on(member.user.uid.eq(user.uid))
        .where(member.user.regDate.eq(now))
        .fetchFirst();
  }
}
