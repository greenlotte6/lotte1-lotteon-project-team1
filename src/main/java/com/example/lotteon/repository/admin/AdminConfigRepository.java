package com.example.lotteon.repository.admin;

import com.example.lotteon.entity.admin.config.ConfigDocument;
import com.example.lotteon.entity.admin.config.CorpInfo;
import com.example.lotteon.entity.admin.config.CustomerServiceInfo;
import com.example.lotteon.entity.admin.config.Logo;
import com.example.lotteon.entity.admin.config.Site;
import com.example.lotteon.entity.admin.config.VersionConfig;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminConfigRepository {

  private final MongoTemplate template;
  private final Gson gson;

  public VersionConfig findVersion() {
    Query query = new Query(Criteria.where("id").is("basic_config::version"));
    return template.findOne(query, VersionConfig.class, "BasicConfig");
  }

  public Site findSite() {
    Query query = new Query(Criteria.where("id").is("basic_config::site"));
    return template.findOne(query, Site.class, "BasicConfig");
  }

  public Logo findLogo() {
    Query query = new Query(Criteria.where("id").is("basic_config::logo"));
    return template.findOne(query, Logo.class, "BasicConfig");
  }

  public CorpInfo findCorpInfo() {
    Query query = new Query(Criteria.where("id").is("basic_config::corp_info"));
    return template.findOne(query, CorpInfo.class, "BasicConfig");
  }

  public CustomerServiceInfo findCsInfo() {
    Query query = new Query(Criteria.where("id").is("basic_config::cs_info"));
    return template.findOne(query, CustomerServiceInfo.class, "BasicConfig");
  }

  public String findCopyright() {
    Query query = new Query(Criteria.where("id").is("basic_config::copyright"));
    query.fields().include("copyright").exclude("_id");
    return template.findDistinct(query, "copyright", "BasicConfig", String.class).get(0);
  }

  public ConfigDocument find() {
    log.info("Retrieving config document from database");
    VersionConfig version = findVersion();
    Site site = findSite();
    Logo logo = findLogo();
    CorpInfo corpInfo = findCorpInfo();
    CustomerServiceInfo csInfo = findCsInfo();
    String copyright = findCopyright();

    return ConfigDocument.builder()
        .version(version)
        .site(site)
        .logo(logo)
        .corpInfo(corpInfo)
        .csInfo(csInfo)
        .copyright(copyright)
        .build();
  }

  public void updateSite(Site config) {
    Query query = new Query(Criteria.where("id").is("basic_config::site"));
    Update update = new Update();
    update.set("title", config.getTitle());
    update.set("subtitle", config.getSubtitle());
    template.updateFirst(query, update, "BasicConfig");
  }

  public void updateLogo(Logo config) {
    Query query = new Query(Criteria.where("id").is("basic_config::logo"));
    Update update = new Update();
    update.set("header_location", config.getHeaderLogoLocation());
    update.set("footer_location", config.getFooterLogoLocation());
    update.set("favicon_location", config.getFaviconLocation());
    template.updateFirst(query, update, "BasicConfig");
  }

  public Logo updateLogoBy(String field, String value) {
    Query query = new Query(Criteria.where("id").is("basic_config::logo"));
    Update update = new Update();
    update.set(field, value);
    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
    return template.findAndModify(query, update, options, Logo.class, "BasicConfig");
  }

  public void updateCorpInfo(CorpInfo config) {
    Query query = new Query(Criteria.where("id").is("basic_config::corp_info"));
    Update update = new Update().set("name", config.getName())
        .set("ceo", config.getCeo())
        .set("business_num", config.getBusinessNumber())
        .set("seller_num", config.getSellerNumber())
        .set("address", config.getAddress())
        .set("address_detail", config.getAddressDetail());
    template.updateFirst(query, update, "BasicConfig");
  }

  public void updateCsInfo(CustomerServiceInfo config) {
    Query query = new Query(Criteria.where("id").is("basic_config::cs_info"));
    Update update = new Update().set("contact", config.getContact())
        .set("office_hour", config.getOfficeHour())
        .set("email", config.getEmail())
        .set("dispute_contact", config.getDisputeContact());
    template.updateFirst(query, update, "BasicConfig");
  }

  public void updateCopyright(String copyright) {
    Query query = new Query(Criteria.where("id").is("basic_config::copyright"));
    Update update = new Update().set("copyright", copyright);
    template.updateFirst(query, update, "BasicConfig");
  }
}
