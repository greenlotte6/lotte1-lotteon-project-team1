package com.example.lotteon.repository.admin;

import com.example.lotteon.entity.admin.config.ConfigDocument;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomAdminConfigRepository {

  ConfigDocument partialUpdate(ConfigDocument document);
}
