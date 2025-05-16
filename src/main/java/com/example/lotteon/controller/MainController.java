package com.example.lotteon.controller;

import com.example.lotteon.entity.admin.banner.BannerDocument;
import com.example.lotteon.service.admin.CacheService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

  private final CacheService cacheService;

  @GetMapping(value = {"/", "/index"})
  public String index(Model model) {
    List<BannerDocument> mainBanners = cacheService.getCachedBanner("MAIN");
    List<BannerDocument> sliderBanners = cacheService.getCachedBanner("SLIDER");

    if (mainBanners != null) {
      int randomIndex = (int) (Math.random() * mainBanners.size());
      BannerDocument randomBanner = mainBanners.get(randomIndex);
      model.addAttribute("mainBanner", randomBanner);
    }

    if (sliderBanners != null) {
      model.addAttribute("sliderBanners", sliderBanners);
    }
    return "/index";
  }


}
