/*
    작성자 : 손준오(sjo112777)
    내용: 답변 달기 서비스
*/
package com.example.lotteon.service.cs;

import com.example.lotteon.dto.cs.QnaDTO;
import com.example.lotteon.dto.cs.ReplyDTO;
import com.example.lotteon.entity.cs.Qna;
import com.example.lotteon.entity.cs.Reply;
import com.example.lotteon.repository.jpa.cs.QnaRepository;
import com.example.lotteon.repository.jpa.cs.ReplyRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyService {

  private final ReplyRepository replyRepository;
  private final ModelMapper modelMapper;
  private final QnaRepository qnaRepository;

  public int save(ReplyDTO replyDTO) {
    // Qna 조회
    Qna qna = qnaRepository.findById(replyDTO.getQna_id())
        .orElseThrow(() -> new RuntimeException("Qna not found"));

    // 답변 저장
    Reply reply = Reply.builder()
        .qna(qna)
        .content(replyDTO.getContent())
        .build();

    // Qna 상태 업데이트: 답변이 달리면 Qna 상태를 'close'로 변경
    qna.setStatus("close");  // status를 'close'로 변경
    qnaRepository.save(qna);  // 변경된 Qna 상태 저장

    // 답변 저장
    Reply savedReply = replyRepository.save(reply);
    return savedReply.getId();
  }

  // 조회 (QnaId로 답변 가져오기)
  public ReplyDTO findByQnaId(int qna_id) {
    Qna qna = qnaRepository.findById(qna_id)
        .orElseThrow(() -> new RuntimeException("Qna not found"));

    Optional<Reply> optReply = replyRepository.findByQna(qna);

    if (optReply.isPresent()) {
      Reply reply = optReply.get();

      ReplyDTO replyDTO = ReplyDTO.builder()
          .id(reply.getId())
          .content(reply.getContent())
          .qna(QnaDTO.builder()
              .id(reply.getQna().getId())
              .member_id(
                  reply.getQna().getMember_id().getMemberId().getUser().getId()) // DTO용 member_id
              .title(reply.getQna().getTitle())
              .content(reply.getQna().getContent())
              .register_date(reply.getQna().getRegister_date().toString()) // LocalDate → String
              .type_id(reply.getQna().getType_id().getId()) // type_id 객체에서 id 꺼내기
              .status(reply.getQna().getStatus())
              .user_id(reply.getQna().getMember_id().getMemberId().getUser().getId()) // 추가필드
              .name(reply.getQna().getMember_id().getName()) // 추가필드
              .subtype_name(reply.getQna().getType_id().getName()) // 추가필드
              .build()
          )
          .build();

      return replyDTO;
    }

    return null;
  }
}
