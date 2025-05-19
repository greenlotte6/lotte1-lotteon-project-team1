package com.example.lotteon.repository.jpa.admin.config;

import com.example.lotteon.entity.admin.banner.BannerDocument;
import com.example.lotteon.entity.admin.banner.BannerInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

//TODO: 시작/만료 날짜가 되면 자동으로 배너 활성/비활성화? -> 배치 작업?

@Repository
@RequiredArgsConstructor
public class BannerRepository {

  private final MongoTemplate template;

  public BannerDocument getBanner(String _id) {
    Query query = new Query(Criteria.where("_id").is(new ObjectId(_id)));
    return template.findOne(query, BannerDocument.class);
  }

  public List<BannerDocument> getBannerByPosition(String position) {
    Query query = new Query(Criteria.where("id").is("admin::banner").and("position").is(position));
    return template.find(query, BannerDocument.class);
  }

  /**
   * 메인페이지, 로그인 페이지, 마이 페이지, 상품 상세 설명 페이지, 메인 페이지 슬라이더에 노출 될 활성화된 배너 데이터를 반환
   *
   * @return 활성화 된 배너 데이터
   */
  public List<BannerDocument> getActiveBanners() {
    Query query = new Query(Criteria.where("id").is("admin::banner").and("status").is("active"));
    return template.find(query, BannerDocument.class);
  }

  public BannerInfo getBannerInfoById(String _id) {
    Query query = new Query(Criteria.where("_id").is(new ObjectId(_id)));
    BannerDocument bannerDocument = template.findOne(query, BannerDocument.class);
    if (bannerDocument == null) {
      return null;
    }
    return bannerDocument.getBannerInfo();
  }

  public void save(BannerDocument bannerDocument) {
    template.insert(bannerDocument);
  }

  public void delete(String _id) {
    Query query = new Query(Criteria.where("_id").is(new ObjectId(_id)));
    template.remove(query, BannerDocument.class);
  }

  public void changeStatus(String _id, String newStatus) {
    Query query = new Query(Criteria.where("_id").is(new ObjectId(_id)));
    Update update = new Update();
    update.set("status", newStatus);
    template.updateFirst(query, update, BannerDocument.class);
  }
}
