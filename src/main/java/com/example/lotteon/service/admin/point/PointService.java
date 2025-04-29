package com.example.lotteon.service.admin.point;

import com.example.lotteon.entity.point.Point;
import com.example.lotteon.repository.admin.point.PointRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

  private final PointRepository repo;

  public Page<Point> findAll(Pageable pageable) {
    return repo.findAll(pageable);
  }

  public Page<Point> findByMemberId(String memberId, Pageable pageable) {
    return repo.findByMemberId(memberId, pageable);
  }

  public Page<Point> findByMemberName(String memberName, Pageable pageable) {
    return repo.findByMemberName(memberName, pageable);
  }

  public Page<Point> findByEmail(String email, Pageable pageable) {
    return repo.findByEmail(email, pageable);
  }

  public Page<Point> findByContact(String contact, Pageable pageable) {
    return repo.findByContact(contact, pageable);
  }

  public void deleteMultipleById(List<Integer> ids) {
    for (Integer id : ids) {
      repo.deleteById(id);
    }
  }
}
