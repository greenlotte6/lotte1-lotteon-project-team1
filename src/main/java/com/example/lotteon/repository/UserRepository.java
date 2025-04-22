package com.example.lotteon.repository;

import com.example.lotteon.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    long countById(String id);
    long countByEmail(String email);
    long countByContact(String contact);

    //Optional<User> findByEmail(String email);
}
