package com.example.lotteon.repository.jpa.cs.custom;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.entity.cs.Qna;
import com.querydsl.core.Tuple;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnaRepositoryCustom {

  public Page<Tuple> selectAllForList(Pageable pageable, int type_id, String name);

  public Page<Tuple> selectAllForSearch(PageRequestDTO pageRequestDTO, Pageable pageable,
      int type_id);

  List<Qna> findLimit(int limit);

  long countAll(LocalDate date);

}
