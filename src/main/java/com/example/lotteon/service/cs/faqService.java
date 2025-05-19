/*
    작성자 : 손준오(sjo112777)
    내용: 자주묻는질문 서비스
*/
package com.example.lotteon.service.cs;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.dto.PageResponseDTO;
import com.example.lotteon.dto.cs.FaqDTO;
import com.example.lotteon.entity.cs.Article_Type;
import com.example.lotteon.entity.cs.Faq;
import com.example.lotteon.repository.jpa.cs.FaqRepository;
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
public class faqService {

  private final FaqRepository faqRepository;
  private final ModelMapper modelMapper;

  public int faqRegister(FaqDTO faqDTO) {

    Faq faq = Faq.builder()
        .title(faqDTO.getTitle())
        .content(faqDTO.getContent())
        .hit(0)
        .register_date(LocalDate.now())
        .type_id(Article_Type.builder().id(faqDTO.getType_id()).build())
        .build();

    //Notice notice = modelMapper.map(noticeDTO, Notice.class);

    //JPA 저장
    Faq savedFaq = faqRepository.save(faq);

    //저장한 글번호 반환
    return savedFaq.getId();
  }

  public FaqDTO findById(int id) {

    Optional<Faq> optFaq = faqRepository.findById(id);

    if (optFaq.isPresent()) {
      Faq faq = optFaq.get();
      Article_Type type = faq.getType_id();

      FaqDTO faqDTO = FaqDTO.builder()
          .id(faq.getId())
          .title(faq.getTitle())
          .content(faq.getContent())
          .register_date(faq.getRegister_date().toString())
          .hit(faq.getHit())
          .type_id(faq.getType_id().getId())
          .name(type.getName())   //1차 유형
          .subtype_name(type.getSubtype_name())   //2차 유형
          .build();

      faq.setHit(faqDTO.getHit() + 1);
      faqRepository.save(faq);

      return faqDTO;

    }
    return null;

  }

  @Transactional
  public void deletefaq(int id) {
    faqRepository.deleteById(id);
  }

  @Transactional
  public void modifyFaq(FaqDTO faqDTO) {

    //pk
    Optional<Faq> optFaq = faqRepository.findById(faqDTO.getId());
    if (optFaq.isPresent()) {
      Faq faq = optFaq.get(); //기존 데이터 조회

      //새로운 값으로 업데이트
      faq.setType_id(Article_Type.builder().id(faqDTO.getType_id()).build());
      faq.setTitle(faqDTO.getTitle());
      faq.setContent(faqDTO.getContent());

      //저장
      faqRepository.save(faq);
    }
  }

  public PageResponseDTO findAll(PageRequestDTO pageRequestDTO, int type_id) {

    // 페이징 처리를 위한 pageable 객체 생성
    Pageable pageable = pageRequestDTO.getPageable("id");

    Page<Tuple> pageFaq = faqRepository.selectAllForList(pageable, type_id);

    List<FaqDTO> faqDTOList = pageFaq.getContent().stream().map(tuple -> {

      Faq faq = tuple.get(0, Faq.class);

      return FaqDTO.builder()
          .id(faq.getId())
          .title(faq.getTitle())
          .content(faq.getContent())
          .hit(faq.getHit())
          .register_date(faq.getRegister_date().toString())
          .type_id(faq.getType_id().getId()) // Article_Type의 id
          .name(faq.getType_id().getName()) // Article_Type의 name
          .subtype_name(faq.getType_id().getSubtype_name()) // Article_Type의 subtype_name
          .build();

    }).toList();

    int total = (int) pageFaq.getTotalElements();

    return PageResponseDTO.<FaqDTO>builder()
        .pageRequestDTO(pageRequestDTO)
        .dtoList(faqDTOList)
        .total(total)
        .build();
  }

  public PageResponseDTO searchAll(PageRequestDTO pageRequestDTO, int typeId) {

    Pageable pageable = pageRequestDTO.getPageable("id");

    Page<Tuple> pageFaq = faqRepository.selectAllForSearch(pageRequestDTO, pageable, typeId);

    List<FaqDTO> faqDTOList = pageFaq.getContent().stream().map(tuple -> {
      Faq faq = tuple.get(0, Faq.class);
      return FaqDTO.builder()
          .id(faq.getId())
          .title(faq.getTitle())
          .content(faq.getContent())
          .hit(faq.getHit())
          .register_date(faq.getRegister_date().toString())
          .type_id(faq.getType_id().getId())
          .name(faq.getType_id().getName()) // Article_Type의 name
          .subtype_name(faq.getType_id().getSubtype_name())
          .build();
    }).toList();

    int total = (int) pageFaq.getTotalElements();

    return PageResponseDTO.<FaqDTO>builder()
        .pageRequestDTO(pageRequestDTO)
        .dtoList(faqDTOList)
        .total(total)
        .build();
  }

}
