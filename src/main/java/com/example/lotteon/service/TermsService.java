package com.example.lotteon.service;

import com.example.lotteon.dto.TermsDTO;
import com.example.lotteon.entity.Terms;
import com.example.lotteon.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TermsService {
    private final TermsRepository termsRepository;
    private final ModelMapper modelMapper;

    public List<TermsDTO> terms(){
        List<Terms> terms = termsRepository.findAll();
        List<TermsDTO> dtos = new ArrayList<>();

        for (Terms term : terms) {
            // modelmapper를 이용한 변환
            dtos.add(modelMapper.map(term, TermsDTO.class));
        }
        return dtos;
    }
}
