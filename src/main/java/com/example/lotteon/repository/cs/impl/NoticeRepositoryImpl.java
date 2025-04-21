package com.example.lotteon.repository.cs.impl;

import com.example.lotteon.entity.cs.QNotice;
import com.example.lotteon.repository.cs.custom.NoticeRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QNotice qNotice = QNotice.notice;

    @Override
    public Page<Tuple> selectAllForList(Pageable pageable, int type_id) {
        BooleanExpression expression = qNotice.type_id.id.eq(type_id);

        long total = queryFactory
                .select(qNotice.count())
                .from(qNotice)
                .where(expression)
                .fetchOne();

        List<Tuple> tupleList = queryFactory
                .select(qNotice, qNotice.type_id.subtype_name) // 필요하면 조인 없이도 가능
                .from(qNotice)
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qNotice.id.desc())
                .fetch();

        // 페이징 처리를 위한 페이지 객체 반환
        return new PageImpl<Tuple>(tupleList, pageable, total);
    }
}
