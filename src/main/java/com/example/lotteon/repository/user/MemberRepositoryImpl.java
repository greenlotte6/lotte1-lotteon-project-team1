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
  public Long countNewMembersOf(LocalDate date) {
    LocalDate now = LocalDate.now();
    return query.select(member.count())
        .from(member)
        .join(user)
        .on(member.userCompositeKey.user.id.eq(user.id))
        .where(member.userCompositeKey.user.registerDate.eq(now))
        .fetchFirst();
  }
}
