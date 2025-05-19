package com.example.lotteon.repository.jpa.admin.point;

import com.example.lotteon.entity.point.Point;
import com.example.lotteon.entity.point.QPoint;
import com.example.lotteon.entity.user.QMember;
import com.example.lotteon.entity.user.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CustomPointRepositoryImpl implements CustomPointRepository {

  private final JPAQueryFactory query;
  private QPoint point = QPoint.point;
  private QMember member = QMember.member;
  private QUser user = QUser.user;

  private List<Point> toPointList(List<Tuple> tuples) {
    List<Point> pointList = new ArrayList<>();
    for (Tuple tuple : tuples) {
      Point current = tuple.get(0, Point.class);
      pointList.add(current);
    }
    return pointList;
  }

  @Override
  public Page<Point> findAll(Pageable pageable) {
    List<Point> pointList = query.selectFrom(point)
        .orderBy(point.issuedDate.asc())
        .fetch();
    return new PageImpl<>(pointList, pageable, pointList.size());
  }

  @Override
  public Page<Point> findByMemberId(String memberId, Pageable pageable) {
    List<Tuple> results = query
        .select(point, member, user)
        .from(point)
        .join(member).on(member.memberId.eq(point.member.memberId))
        .join(user).on(member.memberId.user.id.eq(user.id))
        .where(user.id.eq(memberId))
        .fetch();

    List<Point> pointList = toPointList(results);
    return new PageImpl<>(pointList, pageable, pointList.size());
  }

  @Override
  public Page<Point> findByMemberName(String memberName, Pageable pageable) {
    List<Point> pointList = query.selectFrom(point)
        .where(point.member.name.eq(memberName))
        .orderBy(point.issuedDate.asc())
        .fetch();
    return new PageImpl<>(pointList, pageable, pointList.size());
  }

  @Override
  public Page<Point> findByEmail(String email, Pageable pageable) {
    List<Tuple> results = query
        .select(point, member, user)
        .from(point)
        .join(member).on(member.memberId.eq(point.member.memberId))
        .join(user).on(member.memberId.user.id.eq(user.id))
        .where(user.email.eq(email))
        .fetch();
    List<Point> pointList = toPointList(results);
    return new PageImpl<>(pointList, pageable, pointList.size());
  }

  @Override
  public Page<Point> findByContact(String contact, Pageable pageable) {
    List<Tuple> results = query
        .select(point, member, user)
        .from(point)
        .join(member).on(member.memberId.eq(point.member.memberId))
        .join(user).on(member.memberId.user.id.eq(user.id))
        .where(user.contact.eq(contact))
        .fetch();
    List<Point> pointList = toPointList(results);
    return new PageImpl<>(pointList, pageable, pointList.size());
  }

  @Override
  @Transactional
  public void deleteById(int id) {
    query.delete(this.point)
        .where(this.point.id.eq(id))
        .execute();
  }
}
