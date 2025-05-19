package com.example.lotteon.repository.jpa.admin.config;

import com.example.lotteon.entity.admin.config.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

}
