package com.example.lotteon.service.recruit;

import com.example.lotteon.entity.recruit.Recruit;
import com.example.lotteon.repository.recruit.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitService {

    @Autowired
    private RecruitRepository recruitRepository;

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
}
