package com.example.lotteon.service.cs;

import com.example.lotteon.dto.cs.QnaDTO;
import com.example.lotteon.entity.cs.Article_Type;
import com.example.lotteon.entity.cs.Faq;
import com.example.lotteon.entity.cs.Qna;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.entity.user.UserCompositeKey;
import com.example.lotteon.repository.cs.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Service
public class QnaService {

    private final QnaRepository qnaRepository;
    private final ModelMapper modelMapper;

    public int qnaRegister(QnaDTO qnaDTO) {

        // User 객체 생성
        User user = User.builder()
                .id(qnaDTO.getMember_id()) // QnaDTO에는 String 형태로 들어있음
                .build();

        // UserCompositeKey 객체 생성
        UserCompositeKey key = UserCompositeKey.builder()
                .user(user)
                .build();

        // Member 객체 생성
        Member member = Member.builder()
                .userCompositeKey(key)
                .build();

        // Qna 엔티티 생성
        Qna qna = Qna.builder()
                .member_id(member)
                .title(qnaDTO.getTitle())
                .content(qnaDTO.getContent())
                .register_date(LocalDate.now())
                .type_id(Article_Type.builder().id(qnaDTO.getType_id()).build())
                .status("open")
                .build();


        //JPA 저장
        Qna savedQna = qnaRepository.save(qna);

        //저장한 글번호 반환
        return savedQna.getId();
    }
}
