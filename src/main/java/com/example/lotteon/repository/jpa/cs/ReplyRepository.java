package com.example.lotteon.repository.jpa.cs;

import com.example.lotteon.entity.cs.Qna;
import com.example.lotteon.entity.cs.Reply;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

  Optional<Reply> findByQna(Qna qna);
}
