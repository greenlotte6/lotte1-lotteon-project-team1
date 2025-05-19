package com.example.lotteon.repository.jpa.admin.point;

import com.example.lotteon.entity.point.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long>, CustomPointRepository {

  Page<Point> findByMember_MemberId_User_Id(String userId, Pageable pageable);
}
