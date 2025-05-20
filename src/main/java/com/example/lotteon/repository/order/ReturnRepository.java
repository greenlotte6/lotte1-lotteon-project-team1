package com.example.lotteon.repository.order;

import com.example.lotteon.entity.order.Return;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnRepository extends JpaRepository<Return, Integer>{
}
