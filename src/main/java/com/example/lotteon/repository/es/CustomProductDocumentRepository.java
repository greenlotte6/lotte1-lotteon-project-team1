package com.example.lotteon.repository.es;

import com.example.lotteon.es.document.ProductDocument;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomProductDocumentRepository {

  List<ProductDocument> search(String keyword);
}
