package com.example.lotteon.repository.user;

import com.example.lotteon.entity.user.Member;
import com.example.lotteon.entity.user.QMember;
import com.example.lotteon.entity.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

  private final JPAQueryFactory query;
  private final QMember member = QMember.member;
  private final QUser user = QUser.user;

  @Override
  public Long countNewMembersOf(LocalDate date) {
    return query.select(member.count())
        .from(member)
        .join(user)
        .on(member.userCompositeKey.user.id.eq(user.id))
        .where(member.userCompositeKey.user.registerDate.eq(date))
        .fetchFirst();
  }

  @Override
  public Page<Member> findAll(Pageable pageable) {
    List<Member> members = query.selectFrom(member).fetch();
    return new PageImpl<>(members, pageable, members.size());
  }
}
