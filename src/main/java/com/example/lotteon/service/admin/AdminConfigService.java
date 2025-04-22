package com.example.lotteon.service.admin;

import com.example.lotteon.dto.admin.ConfigDTO;
import com.example.lotteon.entity.admin.config.ConfigDocument;
import com.example.lotteon.repository.admin.AdminConfigRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminConfigService {

  @Value("${lotteon.data.mongodb.admin.config.id}")
  private String docId;

  private final AdminConfigRepository repo;
  private final ModelMapper mapper;

  public ConfigDocument update(ConfigDTO docDTO) {
    docDTO.setId(docId);
    ConfigDocument doc = mapper.map(docDTO, ConfigDocument.class);
    return repo.save(doc);
  }
}
