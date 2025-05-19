package com.example.lotteon.repository.jpa.cs.impl;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.entity.cs.QFaq;
import com.example.lotteon.repository.jpa.cs.custom.FaqRepositoryCustom;
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
public class FaqRepositoryImpl implements FaqRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private QFaq qFaq = QFaq.faq;

  @Override
  public Page<Tuple> selectAllForList(Pageable pageable, int type_id) {
    BooleanExpression expression = null;

    // type_id가 0보다 클 때만 조건 적용 (전체 조회는 조건 없음)
    if (type_id > 0) {
      expression = qFaq.type_id.id.eq(type_id);
    }

    long total = queryFactory
        .select(qFaq.count())
        .from(qFaq)
        .where(expression)  // null이면 조건 없이 전체 조회
        .fetchOne();

    List<Tuple> tupleList = queryFactory
        .select(qFaq, qFaq.type_id.subtype_name)
        .from(qFaq)
        .where(expression)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(qFaq.id.desc())
        .fetch();

    return new PageImpl<>(tupleList, pageable, total);
  }

  @Override
  public Page<Tuple> selectAllForSearch(PageRequestDTO pageRequestDTO, Pageable pageable,
      int type_id) {
    String filter = pageRequestDTO.getFilter();

    BooleanExpression expression = null;

    if (type_id > 0) {
      expression = qFaq.type_id.id.eq(type_id);
    }

    // 여기에 filter(검색 키워드 조건)도 조합할 수 있음
    // 예: expression = expression != null ? expression.and(qNotice.title.contains(filter)) : qNotice.title.contains(filter);

    long total = queryFactory
        .select(qFaq.count())
        .from(qFaq)
        .where(expression)
        .fetchOne();

    List<Tuple> tupleList = queryFactory
        .select(qFaq, qFaq.type_id.subtype_name)
        .from(qFaq)
        .where(expression)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(qFaq.id.desc())
        .fetch();

    return new PageImpl<>(tupleList, pageable, total);
  }
}
