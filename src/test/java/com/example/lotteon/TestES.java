package com.example.lotteon;

import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.es.document.ProductDocument;
import com.example.lotteon.repository.jpa.product.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestES {

  @Autowired
  private ProductRepository productRepo;

  @Autowired
  private ModelMapper mapper;

  @Test
  void test() {
    List<Product> entities = productRepo.findAll();
    List<ProductDTO> products = entities.stream().map((product) -> {
      return mapper.map(product, ProductDTO.class);
    }).toList();
    List<ProductDocument> docs = products.stream().map((product) -> {
      return mapper.map(product, ProductDocument.class);
    }).toList();
    for (ProductDocument doc : docs) {
      System.out.println(doc);
    }
  }
}
