package com.example.lotteon.service;

import com.example.lotteon.dto.TermsDTO;
import com.example.lotteon.entity.Terms;
import com.example.lotteon.repository.jpa.TermsRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TermsService {

  private final TermsRepository termsRepository;
  private final ModelMapper modelMapper;

  public List<TermsDTO> terms() {
    List<Terms> terms = termsRepository.findAll();
    List<TermsDTO> dtos = new ArrayList<>();
    log.info("dtos {}", dtos);

    for (Terms term : terms) {
      // modelmapper를 이용한 변환
      dtos.add(modelMapper.map(term, TermsDTO.class));
    }
    return dtos;
  }
}
