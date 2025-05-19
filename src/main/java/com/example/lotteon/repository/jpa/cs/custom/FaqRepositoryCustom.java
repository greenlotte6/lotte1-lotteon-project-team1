package com.example.lotteon.repository.jpa.cs.custom;

import com.example.lotteon.dto.PageRequestDTO;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FaqRepositoryCustom {

  public Page<Tuple> selectAllForList(Pageable pageable, int type_id);

  public Page<Tuple> selectAllForSearch(PageRequestDTO pageRequestDTO, Pageable pageable,
      int type_id);


}
