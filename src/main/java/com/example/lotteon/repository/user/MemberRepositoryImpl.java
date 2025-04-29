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
import org.springframework.transaction.annotation.Transactional;

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
  public Member findById(String id) {
    return query.selectFrom(member)
        .join(user)
        .on(member.userCompositeKey.user.id.eq(user.id))
        .where(user.id.eq(id))
        .fetchOne();
  }

  @Override
  public Page<Member> findAll(Pageable pageable) {
    List<Member> members = query.selectFrom(member).fetch();
    return new PageImpl<>(members, pageable, members.size());
  }

  @Override
  public Page<Member> findAllById(Pageable pageable, String id) {
    List<Member> members = query.selectFrom(member)
        .join(user)
        .on(member.userCompositeKey.user.id.eq(user.id))
        .where(member.userCompositeKey.user.id.eq(id))
        .fetch();
    return new PageImpl<>(members, pageable, members.size());
  }

  @Override
  public Page<Member> findAllByName(Pageable pageable, String name) {
    List<Member> members = query.selectFrom(member)
        .join(user)
        .on(member.userCompositeKey.user.id.eq(user.id))
        .where(member.name.eq(name))
        .fetch();
    return new PageImpl<>(members, pageable, members.size());
  }

  @Override
  public Page<Member> findAllByEmail(Pageable pageable, String email) {
    List<Member> members = query.selectFrom(member)
        .join(user)
        .on(member.userCompositeKey.user.id.eq(user.id))
        .where(member.name.eq(email))
        .fetch();
    return new PageImpl<>(members, pageable, members.size());
  }

  @Override
  public Page<Member> findAllByContact(Pageable pageable, String contact) {
    List<Member> members = query.selectFrom(member)
        .join(user)
        .on(member.userCompositeKey.user.id.eq(user.id))
        .where(member.name.eq(contact))
        .fetch();
    return new PageImpl<>(members, pageable, members.size());
  }

  @Override
  @Transactional
  public void updateLevel(Member member) {
    query.update(this.member)
        .set(this.member.level, member.getLevel())
        .where(this.member.userCompositeKey.user.id.eq(
            member.getUserCompositeKey().getUser().getId()))
        .execute();
  }

  @Override
  @Transactional
  public void updateStatus(Member member) {
    query.update(this.member)
        .set(this.member.status, member.getStatus())
        .where(
            this.member.userCompositeKey.user.id.eq(member.getUserCompositeKey().getUser().getId()))
        .execute();
  }

  @Override
  @Transactional
  public void updateInfo(Member member) {
    query.update(this.member)
        .set(this.member.name, member.getName())
        .set(this.member.gender, member.getGender())
        .set(this.member.userCompositeKey.user.email,
            member.getUserCompositeKey().getUser().getEmail())
        .set(this.member.userCompositeKey.user.zip, member.getUserCompositeKey().getUser().getZip())
        .set(this.member.userCompositeKey.user.address,
            member.getUserCompositeKey().getUser().getAddress())
        .set(this.member.userCompositeKey.user.addressDetail,
            member.getUserCompositeKey().getUser().getAddressDetail())
        .set(this.member.description, member.getDescription())
        .where(
            this.member.userCompositeKey.user.id.eq(member.getUserCompositeKey().getUser().getId()))
        .execute();
  }
}
