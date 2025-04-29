package com.example.lotteon.repository.admin.point;

import com.example.lotteon.entity.point.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long>, CustomPointRepository {

}
