package com.example.lotteon.repository.user;

import com.example.lotteon.entity.user.Member;
import com.example.lotteon.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, User>, MemberRepositoryCustom {

    // MemberId의 User 엔티티의 id를 기준으로 조회 ( 쿠폰 등록시 쓰임)
    @Query("SELECT m FROM Member m WHERE m.memberId.user.id = :userId")
    Member findByUserId(String userId);

}
