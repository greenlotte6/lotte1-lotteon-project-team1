/*
    작성자 : 손준오(sjo112777)
    내용: 공지사항 서비스
*/
package com.example.lotteon.service.cs;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.dto.PageResponseDTO;
import com.example.lotteon.dto.cs.NoticeDTO;
import com.example.lotteon.entity.cs.Article_Type;
import com.example.lotteon.entity.cs.Notice;
import com.example.lotteon.repository.jpa.cs.NoticeRepository;
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
public class CsService {

  private final NoticeRepository noticeRepository;
  private final ModelMapper modelMapper;

  public int noticeRegister(NoticeDTO noticeDTO) {

    Notice notice = Notice.builder()
        .title(noticeDTO.getTitle())
        .content(noticeDTO.getContent())
        .hit(0)
        .register_date(LocalDate.now())
        .type_id(Article_Type.builder().id(noticeDTO.getType_id()).build())
        .build();

    //Notice notice = modelMapper.map(noticeDTO, Notice.class);

    //JPA 저장
    Notice savedNotice = noticeRepository.save(notice);

    //저장한 글번호 반환
    return savedNotice.getId();
  }

  public NoticeDTO findById(int id) {

    Optional<Notice> optNotice = noticeRepository.findById(id);

    if (optNotice.isPresent()) {
      Notice notice = optNotice.get();
      Article_Type type = notice.getType_id();

      // ⭐ 수동 변환으로 문제 해결
      NoticeDTO noticeDTO = NoticeDTO.builder()
          .id(notice.getId())
          .title(notice.getTitle())
          .content(notice.getContent())
          .register_date(notice.getRegister_date().toString())
          .hit(notice.getHit())
          .type_id(notice.getType_id().getId()) // 핵심
          .subtype_name(type.getSubtype_name())
          .build();

      notice.setHit(noticeDTO.getHit() + 1);
      noticeRepository.save(notice);

      return noticeDTO;

    }
    return null;
  }

  @Transactional
  public void deletenotice(int id) {
    noticeRepository.deleteById(id);
  }

  public PageResponseDTO<NoticeDTO> findAll(PageRequestDTO pageRequestDTO, int type_id) {

    // 페이징 처리를 위한 pageable 객체 생성
    Pageable pageable = pageRequestDTO.getPageable("id");

    // NoticeRepository에서 조회
    Page<Tuple> pageNotice = noticeRepository.selectAllForList(pageable, type_id);

    // DTO로 변환
    List<NoticeDTO> noticeDTOList = pageNotice.getContent().stream().map(tuple -> {
      Notice notice = tuple.get(0, Notice.class);
      return NoticeDTO.builder()
          .id(notice.getId())
          .title(notice.getTitle())
          .content(notice.getContent())
          .hit(notice.getHit())
          .register_date(notice.getRegister_date().toString())
          .type_id(notice.getType_id().getId()) // Article_Type의 id
          .subtype_name(notice.getType_id().getSubtype_name()) // Article_Type의 subtype_name
          .build();
    }).toList();

    // 전체 페이지 개수
    int total = (int) pageNotice.getTotalElements();

    // 결과 반환
    return PageResponseDTO.<NoticeDTO>builder()
        .pageRequestDTO(pageRequestDTO)
        .dtoList(noticeDTOList)
        .total(total)
        .build();
  }

  @Transactional
  public void modifyNotice(NoticeDTO noticeDTO) {

    //pk
    Optional<Notice> optNotice = noticeRepository.findById(noticeDTO.getId());
    if (optNotice.isPresent()) {
      Notice notice = optNotice.get(); //기존 데이터 조회

      //새로운 값으로 업데이트
      notice.setType_id(Article_Type.builder().id(noticeDTO.getType_id()).build());
      notice.setTitle(noticeDTO.getTitle());
      notice.setContent(noticeDTO.getContent());

      //저장
      noticeRepository.save(notice);


    }
  }

  public PageResponseDTO<NoticeDTO> searchAll(PageRequestDTO pageRequestDTO, int typeId) {

    Pageable pageable = pageRequestDTO.getPageable("id");

    Page<Tuple> pageNotice = noticeRepository.selectAllForSearch(pageRequestDTO, pageable, typeId);

    List<NoticeDTO> noticeDTOList = pageNotice.getContent().stream().map(tuple -> {
      Notice notice = tuple.get(0, Notice.class);
      return NoticeDTO.builder()
          .id(notice.getId())
          .title(notice.getTitle())
          .content(notice.getContent())
          .hit(notice.getHit())
          .register_date(notice.getRegister_date().toString())
          .type_id(notice.getType_id().getId())
          .subtype_name(notice.getType_id().getSubtype_name())
          .build();
    }).toList();

    int total = (int) pageNotice.getTotalElements();

    return PageResponseDTO.<NoticeDTO>builder()
        .pageRequestDTO(pageRequestDTO)
        .dtoList(noticeDTOList)
        .total(total)
        .build();
  }

  public List<Notice> getNoticeLimit(int limit) {
    return noticeRepository.findAll();
  }
}
