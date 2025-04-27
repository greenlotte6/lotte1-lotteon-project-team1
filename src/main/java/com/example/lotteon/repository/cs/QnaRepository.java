package com.example.lotteon.repository.cs;

import com.example.lotteon.entity.cs.Qna;
import com.example.lotteon.repository.cs.custom.QnaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Integer>, QnaRepositoryCustom {
}
