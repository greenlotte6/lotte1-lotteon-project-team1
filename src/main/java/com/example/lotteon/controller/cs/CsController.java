package com.example.lotteon.controller.cs;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.dto.PageResponseDTO;
import com.example.lotteon.service.cs.CsService;
import com.example.lotteon.service.cs.faqService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/cs")
public class CsController {

    private final HttpServletRequest request;
    private final CsService csService;
    private final faqService faqService;


    @GetMapping("/csmain")
    public String csmain() {
        return "/cs/csmain";
    }

    @GetMapping("/notice/csnoticeall")
    public String csnoticeall(Model model, PageRequestDTO pageRequestDTO) {

        int type_id = pageRequestDTO.getType_id();
        PageResponseDTO pageResponseDTO = csService.findAll(pageRequestDTO, type_id);

        model.addAttribute(pageResponseDTO);

        return "/cs/notice/csnoticeall";
    }

    @GetMapping("/notice/csnoticecustomer")
    public String csnoticecustomer() {
        return "/cs/notice/csnoticecustomer";
    }

    @GetMapping("/notice/csnoticeevent")
    public String csnoticeevent() {
        return "/cs/notice/csnoticeevent";
    }

    @GetMapping("/notice/csnoticehazard")
    public String csnoticehazard() {
        return "/cs/notice/csnoticehazard";
    }

    @GetMapping("/notice/csnoticesafe")
    public String csnoticesafe() {
        return "/cs/notice/csnoticesafe";
    }

    @GetMapping("/notice/csnoticeview")
    public String csnoticeview() {
        return "/cs/notice/csnoticeview";
    }







    @GetMapping("/cscontactlist")
    public String cscontactlist() {
        return "/cs/cscontactlist";
    }

    @GetMapping("/cscontactview")
    public String cscontactview() {
        return "/cs/cscontactview";
    }

    @GetMapping("/cscontactwrite")
    public String cscontactwrite() {
        return "/cs/cscontactwrite";
    }



    @GetMapping("/csqnalist")
    public String csqnalist() {
        return "/cs/csqnalist";
    }




}
