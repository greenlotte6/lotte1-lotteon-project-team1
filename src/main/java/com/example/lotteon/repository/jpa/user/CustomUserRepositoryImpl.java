package com.example.lotteon.repository.jpa.user;

import com.example.lotteon.entity.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {

  private final JPAQueryFactory query;
  private final QUser user = QUser.user;

  @Override
  public long countNewUsers(LocalDate today) {
    Long count = query.select(user.id.count())
        .from(user)
        .where(user.registerDate.eq(today))
        .fetchOne();
    return count == null ? 0 : count;
  }
}
