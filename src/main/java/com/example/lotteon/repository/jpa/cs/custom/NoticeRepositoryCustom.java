package com.example.lotteon.repository.jpa.cs.custom;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.entity.cs.Notice;
import com.querydsl.core.Tuple;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {

  public Page<Tuple> selectAllForList(Pageable pageable, int type_id);

  public Page<Tuple> selectAllForSearch(PageRequestDTO pageRequestDTO, Pageable pageable,
      int type_id);

  List<Notice> findLimit(int limit);


}
