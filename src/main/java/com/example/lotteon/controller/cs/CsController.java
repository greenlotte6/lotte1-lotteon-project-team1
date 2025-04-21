package com.example.lotteon.controller.cs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cs")
public class CsController {

    @GetMapping("/csmain")
    public String csmain() {
        return "/cs/csmain";
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

    @GetMapping("/notice/csnoticelist")
    public String csnoticelist() {
        return "/cs/notice/csnoticelist";
    }

    @GetMapping("/notice/csnoticeview")
    public String csnoticeview() {
        return "/cs/notice/csnoticeview";
    }

    @GetMapping("/csqnalist")
    public String csqnalist() {
        return "/cs/csqnalist";
    }




}
