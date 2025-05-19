package com.example.lotteon.service.admin;

import com.example.lotteon.dto.admin.PolicyDTO;
import com.example.lotteon.entity.admin.config.Policy;
import com.example.lotteon.repository.jpa.admin.config.PolicyRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyService {

  private final PolicyRepository repo;
  private final ModelMapper mapper;

  public List<PolicyDTO> list() {
    List<Policy> entities = repo.findAll();
    List<PolicyDTO> policies = new ArrayList<>();
    for (Policy entity : entities) {
      PolicyDTO policy = mapper.map(entity, PolicyDTO.class);
      policies.add(policy);
    }
    return policies;
  }

  public void save(PolicyDTO policy) {
    Policy entity = mapper.map(policy, Policy.class);
    repo.save(entity);
  }
}
