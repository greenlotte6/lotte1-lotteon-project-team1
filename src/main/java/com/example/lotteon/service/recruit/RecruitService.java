package com.example.lotteon.service.recruit;

import com.example.lotteon.entity.recruit.Recruit;
import com.example.lotteon.repository.recruit.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitService {

    private final RecruitRepository recruitRepository;

    @Transactional(readOnly = true)
    public List<Recruit> getAllRecruits() {


        return recruitRepository.findAll();
    }
}
