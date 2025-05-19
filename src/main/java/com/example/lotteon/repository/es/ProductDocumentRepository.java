package com.example.lotteon.repository.es;

import com.example.lotteon.es.document.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDocumentRepository extends
    ElasticsearchRepository<ProductDocument, Integer>, CustomProductDocumentRepository {

}
