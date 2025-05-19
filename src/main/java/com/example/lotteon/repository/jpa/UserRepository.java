package com.example.lotteon.repository.jpa;

import com.example.lotteon.entity.user.User;
import com.example.lotteon.repository.jpa.user.CustomUserRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>, CustomUserRepository {

  long countById(String id);

  long countByEmail(String email);

  long countByContact(String contact);


  Optional<User> findByIdAndEmail(String id, String email);
}
