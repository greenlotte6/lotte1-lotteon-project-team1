package com.example.lotteon.repository.jpa.cs;

import com.example.lotteon.entity.cs.Notice;
import com.example.lotteon.repository.jpa.cs.custom.NoticeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer>, NoticeRepositoryCustom {

}
