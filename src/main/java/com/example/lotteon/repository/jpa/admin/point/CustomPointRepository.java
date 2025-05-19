package com.example.lotteon.repository.jpa.admin.point;

import com.example.lotteon.entity.point.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomPointRepository {

  Page<Point> findAll(Pageable pageable);

  Page<Point> findByMemberId(String memberId, Pageable pageable);

  Page<Point> findByMemberName(String memberName, Pageable pageable);

  Page<Point> findByEmail(String email, Pageable pageable);

  Page<Point> findByContact(String contact, Pageable pageable);

  void deleteById(int id);
}
