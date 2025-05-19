package com.example.lotteon.repository.jpa.company;


import com.example.lotteon.entity.company.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
  // 필요한 쿼리 메소드 작성
}
