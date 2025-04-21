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

    @GetMapping("/notice/csnoticeall")
    public String csnoticeall() {
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
