package com.example.lotteon.repository.recruit;

import com.example.lotteon.entity.recruit.Recruit;

import java.awt.print.Pageable;
import java.util.List;

import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {

  @EntityGraph(attributePaths = {"dept", "career", "hireType"})
  List<Recruit> findAll();

  List<Recruit> findByStatus(String status);



}
