package com.example.lotteon.controller.admin.member;

import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.entity.point.Point;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.service.admin.point.PointService;
import com.example.lotteon.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberManagementController {

  private final PointService pointService;
  private final MemberService service;

  @GetMapping("/list")
  public String list(
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      Model model) {
    Pageable pageable = PageRequest.of(page - 1, size);
    Page<Member> members = service.getAll(pageable);
    model.addAttribute("pages", members);
    model.addAttribute("currentPage", page);
    return "/admin/member/member";
  }

  @GetMapping("/search")
  public String search(@RequestParam String filter,
      @RequestParam String keyword,
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      Model model) {
    Pageable pageable = PageRequest.of(page - 1, size);
    Page<Member> pages = null;
    switch (filter) {
      case "id": {
        pages = service.getAllById(pageable, keyword);
        break;
      }
      case "name": {
        pages = service.getAllByName(pageable, keyword);
        break;
      }
      case "email": {
        pages = service.getAllByEmail(pageable, keyword);
        break;
      }
      case "contacnt": {
        pages = service.getAllByContact(pageable, keyword);
        break;
      }
    }
    model.addAttribute("currentPage", page);
    model.addAttribute("pages", pages);
    return "/admin/member/member";
  }

  @PostMapping("/register")
  public String register(@ModelAttribute MemberDTO member) {
    log.info("register member: {}", member);
    service.edit(member);
    return "redirect:/admin/member/list";
  }

  @GetMapping("/point")
  public String point(@RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      Model model) {
    Pageable pageable = PageRequest.of(page - 1, size);
    Page<Point> pages = pointService.findAll(pageable);

    model.addAttribute("currentPage", page);
    model.addAttribute("pages", pages);
    return "/admin/member/point";
  }

  @GetMapping("/point/search")
  public String searchPoint(@RequestParam("filter") String filter,
      @RequestParam("keyword") String keyword,
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      Model model) {
    Pageable pageable = PageRequest.of(page - 1, size);

    Page<Point> pages = null;
    switch (filter) {
      case "id": {
        pages = pointService.findByMemberId(keyword, pageable);
        break;
      }
      case "name": {
        pages = pointService.findByMemberName(keyword, pageable);
        break;
      }
      case "email": {
        pages = pointService.findByEmail(keyword, pageable);
        break;
      }
      case "contact": {
        pages = pointService.findByContact(keyword, pageable);
        break;
      }
    }

    model.addAttribute("currentPage", page);
    model.addAttribute("pages", pages);

    return "/admin/member/point";
  }
}
