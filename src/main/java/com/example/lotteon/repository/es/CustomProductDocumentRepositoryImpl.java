package com.example.lotteon.repository.es;

import com.example.lotteon.es.document.ProductDocument;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomProductDocumentRepositoryImpl implements CustomProductDocumentRepository {

  private final ElasticsearchTemplate template;

  @Override
  public List<ProductDocument> search(String keyword) {
    Query query = NativeQuery.builder()
        .withQuery(q -> q
            .queryString(qs -> qs
                .query("*" + keyword + "*")
                .fields("name", "description")))
        .withPageable(PageRequest.of(0, 8))
        .withFields("_id", "name")
        .withSourceFilter(new FetchSourceFilter(new String[]{"name"}, null))
        .build();

    SearchHits<ProductDocument> hits = template.search(query, ProductDocument.class);
    return hits.stream().map((hit) -> {
      ProductDocument doc = hit.getContent();
      doc.setId(Integer.parseInt(hit.getId()));
      return doc;
    }).toList();
  }
}
