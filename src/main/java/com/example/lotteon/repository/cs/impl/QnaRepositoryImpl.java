package com.example.lotteon.repository.cs.impl;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.entity.cs.QArticle_Type;
import com.example.lotteon.entity.cs.QFaq;
import com.example.lotteon.entity.cs.QQna;
import com.example.lotteon.repository.cs.custom.FaqRepositoryCustom;
import com.example.lotteon.repository.cs.custom.QnaRepositoryCustom;
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
public class QnaRepositoryImpl implements QnaRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QQna qQna = QQna.qna;

    @Override
    public Page<Tuple> selectAllForList(Pageable pageable, int type_id, String name) {
        QArticle_Type qType = QArticle_Type.article_Type;

        BooleanExpression expression = null;

        if (type_id > 0 && name != null && !name.isEmpty()) {
            expression = qQna.type_id.id.eq(type_id)
                    .and(qQna.type_id.name.contains(name));
        } else if (type_id > 0) {
            expression = qQna.type_id.id.eq(type_id);
        } else if (name != null && !name.isEmpty()) {
            expression = qQna.type_id.name.contains(name);
        }

        long total = queryFactory
                .select(qQna.count())
                .from(qQna)
                .join(qQna.type_id, qType)  // ✅ 조인
                .where(expression)
                .fetchOne();

        List<Tuple> tupleList = queryFactory
                .select(qQna, qType.subtype_name, qQna.status)  // status 추가
                .from(qQna)
                .join(qQna.type_id, qType)  // ✅ 조인
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qQna.id.desc())
                .fetch();

        return new PageImpl<>(tupleList, pageable, total);
    }

    @Override
    public Page<Tuple> selectAllForSearch(PageRequestDTO pageRequestDTO, Pageable pageable, int type_id) {
        String filter = pageRequestDTO.getFilter();

        BooleanExpression expression = null;

        if (type_id > 0) {
            expression = qQna.type_id.id.eq(type_id);
        }

        // 여기에 filter(검색 키워드 조건)도 조합할 수 있음
        // 예: expression = expression != null ? expression.and(qNotice.title.contains(filter)) : qNotice.title.contains(filter);

        long total = queryFactory
                .select(qQna.count())
                .from(qQna)
                .where(expression)
                .fetchOne();

        List<Tuple> tupleList = queryFactory
                .select(qQna, qQna.type_id.subtype_name)
                .from(qQna)
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qQna.id.desc())
                .fetch();

        return new PageImpl<>(tupleList, pageable, total);
    }

}
