package com.example.lotteon.service.es;

import com.example.lotteon.es.document.ProductDocument;
import com.example.lotteon.repository.es.ProductDocumentRepository;
import com.google.gson.Gson;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchProductService {

  private final ProductDocumentRepository docRepo;
  private final Gson gson;

  public String search(String keyword) {
    List<ProductDocument> results = docRepo.search(keyword);
    return gson.toJson(results);
  }
}
