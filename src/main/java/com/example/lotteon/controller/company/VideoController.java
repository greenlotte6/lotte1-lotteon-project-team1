package com.example.lotteon.controller.company;

import com.example.lotteon.entity.company.Video;

import com.example.lotteon.service.company.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/company/media")
    public String list(Model model) {
        List<Video> videos = videoService.getAllVideos();  // 서비스에서 데이터를 가져옴
        log.info("list videos : {}", videos);

        model.addAttribute("videos", videos);  // 템플릿에 전달

        return "/common/company/media";
    }



}
