/*
    작성자 : 손준오(sjo112777)
    내용: 관리자 고객센터 컨트롤러
*/
package com.example.lotteon.controller.admin.cs;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.dto.PageResponseDTO;
import com.example.lotteon.dto.cs.FaqDTO;
import com.example.lotteon.dto.cs.NoticeDTO;
import com.example.lotteon.dto.cs.QnaDTO;
import com.example.lotteon.dto.cs.ReplyDTO;
import com.example.lotteon.service.cs.CsService;
import com.example.lotteon.service.cs.QnaService;
import com.example.lotteon.service.cs.ReplyService;
import com.example.lotteon.service.cs.faqService;
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
    private final faqService faqService;
    private final QnaService qnaService;
    private final ReplyService replyService;


    /* 여기부터 노티스 컨트롤러 */
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
    /* 위까지 노티스 컨트롤러 */



    /* 여기부터 faq 자주묻는질문 컨트롤러*/
    @GetMapping("/faq/write")
    public String faqwrite() {
        return "/admin/cs/faq/write";
    }

    @PostMapping("/faq/write")
    public String faqwrite(FaqDTO faqDTO) {

        int id = faqService.faqRegister(faqDTO);
        log.info("faqDTO = {}", faqDTO);
        return "redirect:/admin/cs/faq/list";
    }

    @GetMapping("/faq/view")
    public String faqview(@RequestParam("id")int id, Model model) {

        // 글 조회 서비스 호출
        FaqDTO faqDTO = faqService.findById(id);
        model.addAttribute("faqDTO", faqDTO);

        return "/admin/cs/faq/view";
    }

    @GetMapping("/faq/delete")
    public String faqdelete(int id) {

        faqService.deletefaq(id);

        return "redirect:/admin/cs/faq/list";
    }

    @GetMapping("/faq/modify")
    public String faqmodify(int id, Model model) {

        // 수정 데이터 조회 서비스
        FaqDTO faqDTO = faqService.findById(id);
        //모델참조
        model.addAttribute(faqDTO);

        return "/admin/cs/faq/modify";
    }

    @PostMapping("/faq/modify")
    public String faqmodify(FaqDTO faqDTO) {

        // 서비스호출
        faqService.modifyFaq(faqDTO);


        return "redirect:/admin/cs/faq/list";
    }

    @GetMapping("/faq/list")
    public String faqlist(Model model, PageRequestDTO pageRequestDTO) {

        int type_id = pageRequestDTO.getType_id();
        PageResponseDTO pageResponseDTO = faqService.findAll(pageRequestDTO, type_id);

        model.addAttribute(pageResponseDTO);
        return "/admin/cs/faq/list";
    }

    @GetMapping("/faq/search")
    public String faqsearch(PageRequestDTO pageRequestDTO, Model model) {

        int type_id = pageRequestDTO.getType_id();
        //서비스 호출
        PageResponseDTO pageResponseDTO = faqService.searchAll(pageRequestDTO, type_id);

        model.addAttribute(pageResponseDTO);
        return "/admin/cs/faq/searchlist";

    }


    /*여기 부터 qna 목록*/
    @GetMapping("/qna/list")
    public String qnalist(Model model, PageRequestDTO pageRequestDTO) {

        int type_id = pageRequestDTO.getType_id();
        PageResponseDTO pageResponseDTO = qnaService.findAll(pageRequestDTO, type_id);

        model.addAttribute(pageResponseDTO);
        return "/admin/cs/qna/list";
    }

    @GetMapping("/qna/search")
    public String qnasearch(PageRequestDTO pageRequestDTO, Model model) {

        int type_id = pageRequestDTO.getType_id();
        //서비스 호출
        PageResponseDTO pageResponseDTO = qnaService.searchAll(pageRequestDTO, type_id);

        model.addAttribute(pageResponseDTO);
        return "/admin/cs/qna/searchlist";

    }

    @GetMapping("/qna/view")
    public String qnaview(@RequestParam("id")int id, Model model) {

        // 글 조회 서비스 호출
        QnaDTO qnaDTO = qnaService.findById(id);
        model.addAttribute("qnaDTO", qnaDTO);

        // 답글 조회
        ReplyDTO replyDTO = replyService.findByQnaId(id);
        model.addAttribute("replyDTO", replyDTO);

        return "/admin/cs/qna/view";
    }

    @GetMapping("/qna/delete")
    public String qnadelete(int id) {

        qnaService.deletefaq(id);

        return "redirect:/admin/cs/qna/list";
    }


}
