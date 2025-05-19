package com.example.lotteon.repository.jpa.coupon.custom;

import com.example.lotteon.dto.PageRequestDTO;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponHistoryRepositoryCustom {

  public Page<Tuple> selectAllForList(Pageable pageable, int id);

  public Page<Tuple> selectAllForSearch(PageRequestDTO pageRequestDTO, Pageable pageable, int id,
      String name, int coupon_id, String user_id);

}
