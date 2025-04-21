package com.example.lotteon.controller.admin;

import com.example.lotteon.dto.cs.NoticeDTO;
import com.example.lotteon.service.cs.CsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/cs")
public class AdminCsController {

    private final HttpServletRequest request;
    private final CsService csService;

    @GetMapping("/notice/notice")
    public String notice() {
        return "/admin/cs/notice/notice";
    }

    @GetMapping("/notice/write")
    public String write() {
        return "/admin/cs/notice/write";
    }

    @PostMapping("/notice/write")
    public String write(NoticeDTO noticeDTO) {

        int id = csService.noticeRegister(noticeDTO);
        log.info("noticeDTO = {}", noticeDTO);
        return "redirect:/admin/cs/notice/notice";
    }


    @GetMapping("/notice/view")
    public String view(@RequestParam("id")int id, Model model) {

        // 글 조회 서비스 호출
        NoticeDTO noticeDTO = csService.findById(id);
        model.addAttribute("noticeDTO", noticeDTO);

        return "/admin/cs/notice/view";
    }

    @GetMapping("/notice/modify")
    public String modify() {
        return "/admin/cs/notice/modify";
    }

}
