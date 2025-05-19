/*
    작성자 : 손준오(sjo112777)
    내용: 문의하기 서비스
*/
package com.example.lotteon.service.cs;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.dto.PageResponseDTO;
import com.example.lotteon.dto.cs.QnaDTO;
import com.example.lotteon.entity.cs.Article_Type;
import com.example.lotteon.entity.cs.Qna;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.entity.user.MemberId;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.repository.jpa.cs.QnaRepository;
import com.querydsl.core.Tuple;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    MemberId key = MemberId.builder()
        .user(user)
        .build();

    // Member 객체 생성
    Member member = Member.builder()
        .memberId(key)
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

  public PageResponseDTO findAll(PageRequestDTO pageRequestDTO, int type_id) {
    // 페이징 처리를 위한 pageable 객체 생성
    Pageable pageable = pageRequestDTO.getPageable("id");
    String name = pageRequestDTO.getName();

    Page<Tuple> pageQna = qnaRepository.selectAllForList(pageable, type_id, name);

    List<QnaDTO> qnaDTOList = pageQna.getContent().stream().map(tuple -> {

      Qna qna = tuple.get(0, Qna.class);

      return QnaDTO.builder()
          .id(qna.getId())
          .member_id(qna.getMember_id().getMemberId().getUser().getId())
          .title(qna.getTitle())
          .content(qna.getContent())
          .register_date(qna.getRegister_date().toString())
          .type_id(qna.getType_id().getId()) // Article_Type의 id
          .name(qna.getType_id().getName()) // Article_Type의 name
          .subtype_name(qna.getType_id().getSubtype_name()) // Article_Type의 subtype_name
          .status(qna.getStatus())
          .build();

    }).toList();

    int total = (int) pageQna.getTotalElements();

    return PageResponseDTO.<QnaDTO>builder()
        .pageRequestDTO(pageRequestDTO)
        .dtoList(qnaDTOList)
        .total(total)
        .build();
  }

  public PageResponseDTO searchAll(PageRequestDTO pageRequestDTO, int typeId) {
    Pageable pageable = pageRequestDTO.getPageable("id");

    Page<Tuple> pageQna = qnaRepository.selectAllForSearch(pageRequestDTO, pageable, typeId);

    List<QnaDTO> qnaDTOList = pageQna.getContent().stream().map(tuple -> {
      Qna qna = tuple.get(0, Qna.class);
      return QnaDTO.builder()
          .id(qna.getId())
          .member_id(qna.getMember_id().getMemberId().getUser().getId())
          .title(qna.getTitle())
          .content(qna.getContent())
          .register_date(qna.getRegister_date().toString())
          .type_id(qna.getType_id().getId()) // Article_Type의 id
          .name(qna.getType_id().getName()) // Article_Type의 name
          .subtype_name(qna.getType_id().getSubtype_name()) // Article_Type의 subtype_name
          .status(qna.getStatus())
          .build();
    }).toList();

    int total = (int) pageQna.getTotalElements();

    return PageResponseDTO.<QnaDTO>builder()
        .pageRequestDTO(pageRequestDTO)
        .dtoList(qnaDTOList)
        .total(total)
        .build();
  }


  public QnaDTO findById(int id) {

    Optional<Qna> optQna = qnaRepository.findById(id);

    if (optQna.isPresent()) {
      Qna qna = optQna.get();
      Article_Type type = qna.getType_id();

      QnaDTO qnaDTO = QnaDTO.builder()
          .id(qna.getId())
          .member_id(qna.getMember_id().getMemberId().getUser().getId())
          .title(qna.getTitle())
          .content(qna.getContent())
          .register_date(qna.getRegister_date().toString())
          .type_id(qna.getType_id().getId()) // Article_Type의 id
          .name(qna.getType_id().getName()) // Article_Type의 name
          .subtype_name(qna.getType_id().getSubtype_name()) // Article_Type의 subtype_name
          .status(qna.getStatus())
          .build();

      return qnaDTO;

    }
    return null;

  }

  @Transactional
  public void deletefaq(int id) {
    qnaRepository.deleteById(id);
  }

  // 기존마이페이지 문의하기에서 유저별 문의하기 리스트 출력 >> 페이징 + 글번호 순차 추가
  //public List<QnaDTO> findByUserId(String userId, Pageable pageable) {
  public Page<QnaDTO> findByUserId(String userId, Pageable pageable) {
    // 여기서 qnaRepository 호출
    //List<Qna> qnaList = qnaRepository.findByUserIdOrderByRegisterDateDesc(userId);

    Page<Qna> qnaPage = qnaRepository.findByUserIdOrderByRegisterDateDesc(userId, pageable);

    // QnaDTO로 변환
    //return qnaList.stream().map(qna -> QnaDTO.builder()
    return qnaPage.map(qna -> QnaDTO.builder()
        .id(qna.getId())
        .member_id(qna.getMember_id().getMemberId().getUser().getId())
        .title(qna.getTitle())
        .content(qna.getContent())
        .register_date(qna.getRegister_date().toString())
        .type_id(qna.getType_id().getId())
        .name(qna.getType_id().getName())
        .subtype_name(qna.getType_id().getSubtype_name())
        .status(qna.getStatus())
        .build());
  }

  public List<Qna> getWithLimit(int limit) {
    return qnaRepository.findLimit(limit);
  }


}
