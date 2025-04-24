package com.example.lotteon.service.recruit;

import com.example.lotteon.entity.recruit.Recruit;      // ← 패키지 경로 수정
import com.example.lotteon.repository.recruit.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitService {

    private final RecruitRepository repo;

    public List<Recruit> getAll() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Recruit> getActive() {
        return repo.findByStatus("모집중");
    }
}

