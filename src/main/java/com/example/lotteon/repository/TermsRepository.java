package com.example.lotteon.repository;

import com.example.lotteon.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsRepository extends JpaRepository<Terms, Integer> {
}
