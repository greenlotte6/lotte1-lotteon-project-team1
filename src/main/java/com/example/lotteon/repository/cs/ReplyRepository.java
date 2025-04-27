package com.example.lotteon.repository.cs;

import com.example.lotteon.entity.cs.Qna;
import com.example.lotteon.entity.cs.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    Optional<Reply> findByQna(Qna qna);
}
