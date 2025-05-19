package com.example.lotteon.repository.jpa.admin.config;

import com.example.lotteon.entity.admin.config.ConfigDocument;
import com.example.lotteon.entity.admin.config.CorpInfo;
import com.example.lotteon.entity.admin.config.CustomerServiceInfo;
import com.example.lotteon.entity.admin.config.Logo;
import com.example.lotteon.entity.admin.config.Site;
import com.example.lotteon.entity.admin.config.VersionConfig;
import com.example.lotteon.exception.NoDocumentFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BasicConfigRepository {

  private final MongoTemplate template;

  public VersionConfig findLatestVersion() {
    Query query = new Query(Criteria.where("id").is("basic_config::latest_version"));
    return template.findOne(query, VersionConfig.class, "BasicConfig");
  }

  @Transactional
  public void updateLatestVersion(VersionConfig config) {
    Query query = new Query(Criteria.where("id").is("basic_config::latest_version"));
    Update update = new Update();
    update.set("version", config.getVersion());
    update.set("author", config.getAuthor());
    update.set("description", config.getDescription());
    update.set("created_at", LocalDateTime.now());
    template.updateFirst(query, update, "BasicConfig");
  }

  public List<VersionConfig> findVersions() {
    Query query = new Query(Criteria.where("id").is("basic_config::version"));
    return template.find(query, VersionConfig.class, "BasicConfig");
  }

  public VersionConfig findVersionByName(String versionName) throws NoDocumentFoundException {
    Query query = new Query(
        Criteria.where("id").is("basic_config::version").and("version").is(versionName));
    VersionConfig config = template.findOne(query, VersionConfig.class, "BasicConfig");
    if (config == null) {
      String message = String.format("Cannot find version document %s", versionName);
      throw new NoDocumentFoundException(message);
    }
    return config;
  }

  public void save(VersionConfig config) {
    template.insert(config, "BasicConfig");
  }

  @Transactional
  public void deleteById(String _id) {
    Query query = new Query(Criteria.where("_id").is(_id));
    template.remove(query, "BasicConfig");
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
    VersionConfig latestVersion = findLatestVersion();
    List<VersionConfig> versions = findVersions();
    Site site = findSite();
    Logo logo = findLogo();
    CorpInfo corpInfo = findCorpInfo();
    CustomerServiceInfo csInfo = findCsInfo();
    String copyright = findCopyright();

    return ConfigDocument.builder()
        .latestVersion(latestVersion)
        .versions(versions)
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
