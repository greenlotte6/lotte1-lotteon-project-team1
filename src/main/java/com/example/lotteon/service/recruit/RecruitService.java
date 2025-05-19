package com.example.lotteon.service.recruit;

import com.example.lotteon.entity.recruit.Recruit;
import com.example.lotteon.repository.jpa.recruit.RecruitRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitService {

  private final RecruitRepository recruitRepository;

  public List<Recruit> getAll() {
    return recruitRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
  }

  public List<Recruit> getActive() {
    return recruitRepository.findByStatus("모집중");
  }

  public Recruit getRecruitById(Long id) {
    return recruitRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 채용공고가 없습니다. id=" + id));
  }

  @Transactional(readOnly = true)
  public List<Recruit> getAllRecruits() {

    return recruitRepository.findAll();
  }


  public void saveRecruit(Recruit recruit) {
    recruitRepository.save(recruit);
  }

  public List<Recruit> findAll() {
    return recruitRepository.findAll();
  }

  public void deleteByIds(List<Long> ids) {
    recruitRepository.deleteAllById(ids);
  }


}

