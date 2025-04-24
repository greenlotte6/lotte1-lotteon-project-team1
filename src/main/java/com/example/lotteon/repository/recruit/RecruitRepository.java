package com.example.lotteon.repository.recruit;

import com.example.lotteon.entity.recruit.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    List<Recruit> findByStatus(String status);
    // 필요한 추가 메서드가 있으면 여기 작성
}
