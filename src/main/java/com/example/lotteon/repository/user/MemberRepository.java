package com.example.lotteon.repository.user;

import com.example.lotteon.entity.user.Member;
import com.example.lotteon.entity.user.MemberId;
import com.example.lotteon.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, MemberId>, MemberRepositoryCustom {

    // 기존: 쿠폰 등록 등에서 사용
    @Query("SELECT m FROM Member m WHERE m.memberId.user.id = :userId")
    Member findByUserId(String userId);

    // 새로 추가: Optional로 반환하여 ifPresent 등으로 안전하게 사용 가능
    @Query("SELECT m FROM Member m WHERE m.memberId.user.id = :userId")
    Optional<Member> findOptionalByUserId(@Param("userId") String userId);
}
