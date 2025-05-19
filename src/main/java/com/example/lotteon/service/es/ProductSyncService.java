package com.example.lotteon.service.es;

import com.example.lotteon.entity.product.Product;
import com.example.lotteon.es.document.ProductDocument;
import com.example.lotteon.repository.es.ProductDocumentRepository;
import com.example.lotteon.repository.jpa.product.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSyncService {

  private final ProductRepository productRepo;
  private final ProductDocumentRepository productDocRepo;
  private final ModelMapper mapper;

  public void syncAll() {
    List<Product> products = productRepo.findAll();
    List<ProductDocument> docs = products.stream().map((product) -> {
      return mapper.map(product, ProductDocument.class);
    }).toList();
    productDocRepo.saveAll(docs);
  }

  public void sync(Product product) {
    ProductDocument doc = mapper.map(product, ProductDocument.class);
    productDocRepo.save(doc);
  }
}
