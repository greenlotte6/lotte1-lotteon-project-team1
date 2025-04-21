package com.example.lotteon.repository.user;

import com.example.lotteon.entity.user.QMember;
import com.example.lotteon.entity.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

  private final JPAQueryFactory query;
  private final QMember member = QMember.member;
  private final QUser user = QUser.user;

  @Override
  public Long countNewMembers() {
    LocalDate now = LocalDate.now();
    return query.select(member.count())
        .join(user)
        .on(member.user.id.eq(user.id))
        .where(member.user.registerDate.eq(now))
        .fetchFirst();
  }
}
