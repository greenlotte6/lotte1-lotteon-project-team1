package com.example.lotteon.controller.admin;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.dto.PageResponseDTO;
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

    @GetMapping("/notice/search")
    public String noticesearch(PageRequestDTO pageRequestDTO, Model model) {

        int type_id = pageRequestDTO.getType_id();
        //서비스 호출
        PageResponseDTO pageResponseDTO = csService.searchAll(pageRequestDTO, type_id);

        model.addAttribute(pageResponseDTO);
        return "/admin/cs/notice/searchnotice";

    }

    @GetMapping("/notice/notice")
    public String notice(Model model, PageRequestDTO pageRequestDTO) {

        int type_id = pageRequestDTO.getType_id();
        PageResponseDTO pageResponseDTO = csService.findAll(pageRequestDTO, type_id);

        model.addAttribute(pageResponseDTO);
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
    public String modify(int id, Model model) {

        // 수정 데이터 조회 서비스
        NoticeDTO noticeDTO = csService.findById(id);
        //모델참조
        model.addAttribute(noticeDTO);

        return "/admin/cs/notice/modify";
    }

    @PostMapping("/notice/modify")
    public String modify(NoticeDTO noticeDTO) {

        // 서비스호출
        csService.modifyNotice(noticeDTO);


        return "redirect:/admin/cs/notice/notice";
    }



    @GetMapping("/notice/delete")
    public String delete(int id) {

        csService.deletenotice(id);

        return "redirect:/admin/cs/notice/notice";
    }

}
