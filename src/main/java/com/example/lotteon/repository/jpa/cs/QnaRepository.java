package com.example.lotteon.repository.jpa.cs;

import com.example.lotteon.entity.cs.Qna;
import com.example.lotteon.repository.jpa.cs.custom.QnaRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QnaRepository extends JpaRepository<Qna, Integer>, QnaRepositoryCustom {

  //@Query("SELECT q FROM Qna q WHERE q.member_id.memberId.user.id = :userId ORDER BY q.register_date DESC")
  //List<Qna> findByUserIdOrderByRegisterDateDesc(@Param("userId") String userId);
  @Query("SELECT q FROM Qna q WHERE q.member_id.memberId.user.id = :userId ORDER BY q.register_date DESC, q.id DESC")
  Page<Qna> findByUserIdOrderByRegisterDateDesc(@Param("userId") String userId, Pageable pageable);
}
