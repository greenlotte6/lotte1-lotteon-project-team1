package com.example.lotteon.repository.user;

import com.example.lotteon.entity.user.Member;
import com.example.lotteon.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, User>, MemberRepositoryCustom {

}
