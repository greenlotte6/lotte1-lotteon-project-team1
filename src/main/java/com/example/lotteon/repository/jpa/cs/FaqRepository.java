package com.example.lotteon.repository.jpa.cs;

import com.example.lotteon.entity.cs.Faq;
import com.example.lotteon.repository.jpa.cs.custom.FaqRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Integer>, FaqRepositoryCustom {

}
