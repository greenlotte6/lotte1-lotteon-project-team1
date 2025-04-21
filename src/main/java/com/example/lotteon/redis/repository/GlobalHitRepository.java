package com.example.lotteon.redis.repository;

import com.example.lotteon.redis.entity.GlobalHit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalHitRepository extends CrudRepository<GlobalHit, String> {

}
