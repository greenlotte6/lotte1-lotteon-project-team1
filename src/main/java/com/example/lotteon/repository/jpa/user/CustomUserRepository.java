package com.example.lotteon.repository.jpa.user;

import java.time.LocalDate;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepository {

  long countNewUsers(LocalDate today);
}
