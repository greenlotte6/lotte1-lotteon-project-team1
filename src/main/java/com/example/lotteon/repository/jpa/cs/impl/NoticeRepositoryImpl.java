package com.example.lotteon.repository.jpa.cs.impl;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.entity.cs.Notice;
import com.example.lotteon.entity.cs.QNotice;
import com.example.lotteon.repository.jpa.cs.custom.NoticeRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private QNotice qNotice = QNotice.notice;

  @Override
  public Page<Tuple> selectAllForList(Pageable pageable, int type_id) {
    BooleanExpression expression = null;

    // type_id가 0보다 클 때만 조건 적용 (전체 조회는 조건 없음)
    if (type_id > 0) {
      expression = qNotice.type_id.id.eq(type_id);
    }

    long total = queryFactory
        .select(qNotice.count())
        .from(qNotice)
        .where(expression)  // null이면 조건 없이 전체 조회
        .fetchOne();

    List<Tuple> tupleList = queryFactory
        .select(qNotice, qNotice.type_id.subtype_name)
        .from(qNotice)
        .where(expression)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(qNotice.id.desc())
        .fetch();

    return new PageImpl<>(tupleList, pageable, total);
  }

  @Override
  public Page<Tuple> selectAllForSearch(PageRequestDTO pageRequestDTO, Pageable pageable,
      int type_id) {
    String filter = pageRequestDTO.getFilter();

    BooleanExpression expression = null;

    if (type_id > 0) {
      expression = qNotice.type_id.id.eq(type_id);
    }

    // 여기에 filter(검색 키워드 조건)도 조합할 수 있음
    // 예: expression = expression != null ? expression.and(qNotice.title.contains(filter)) : qNotice.title.contains(filter);

    long total = queryFactory
        .select(qNotice.count())
        .from(qNotice)
        .where(expression)
        .fetchOne();

    List<Tuple> tupleList = queryFactory
        .select(qNotice, qNotice.type_id.subtype_name)
        .from(qNotice)
        .where(expression)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(qNotice.id.desc())
        .fetch();

    return new PageImpl<>(tupleList, pageable, total);
  }

  @Override
  public List<Notice> findLimit(int limit) {
    return queryFactory.selectFrom(qNotice)
        .limit(limit)
        .orderBy(qNotice.register_date.desc())
        .fetch();
  }
}
