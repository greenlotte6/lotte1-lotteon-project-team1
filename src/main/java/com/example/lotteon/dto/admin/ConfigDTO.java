package com.example.lotteon.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigDTO {

  private String id;

  private String version;

  private SiteDTO site;

  private LogoDTO logo;

  private CorpInfoDTO corpInfo;

  private CustomerServiceInfoDTO csInfo;

  private String copyright;

}
