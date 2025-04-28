package com.example.lotteon.repository.recruit;

import com.example.lotteon.entity.recruit.Recruit;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    @EntityGraph(attributePaths = {"dept", "career", "hireType"})
    List<Recruit> findAll();

}
