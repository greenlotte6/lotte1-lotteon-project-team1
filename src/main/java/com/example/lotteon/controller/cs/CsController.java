/*
    작성자 : 손준오(sjo112777)
    내용: 일반고객 고객센터 컨트롤러
*/
package com.example.lotteon.controller.cs;

import com.example.lotteon.dto.PageRequestDTO;
import com.example.lotteon.dto.PageResponseDTO;
import com.example.lotteon.dto.cs.FaqDTO;
import com.example.lotteon.dto.cs.NoticeDTO;
import com.example.lotteon.dto.cs.QnaDTO;
import com.example.lotteon.dto.cs.ReplyDTO;
import com.example.lotteon.security.MyUserDetails;
import com.example.lotteon.service.cs.CsService;
import com.example.lotteon.service.cs.QnaService;
import com.example.lotteon.service.cs.ReplyService;
import com.example.lotteon.service.cs.faqService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/cs")
public class CsController {

    private final HttpServletRequest request;
    private final CsService csService;
    private final faqService faqService;
    private final QnaService qnaService;
    private final ReplyService replyService;


  @GetMapping("/csmain")
  public String csmain(Model model, PageRequestDTO pageRequestDTO) {

    // 메인 페이지에서만 최신 5개 요청
    pageRequestDTO.setIsMainPage(true);

    int type_id = pageRequestDTO.getType_id();
    PageResponseDTO pageResponseDTO = csService.findAll(pageRequestDTO, type_id);
    model.addAttribute("pageResponseDTO", pageResponseDTO);

    // Q&A 리스트 출력
    PageResponseDTO qnaPageResponseDTO = qnaService.findAll(pageRequestDTO, type_id);
    model.addAttribute("qnaPageResponseDTO", qnaPageResponseDTO);


    return "/cs/csmain";
  }

  /*전체*/
  @GetMapping("/notice/csnoticeall")
  public String csnoticeall(Model model, PageRequestDTO pageRequestDTO) {

    int type_id = pageRequestDTO.getType_id();
    PageResponseDTO pageResponseDTO = csService.findAll(pageRequestDTO, type_id);

    model.addAttribute(pageResponseDTO);

    return "/cs/notice/csnoticeall";
  }

  /*고객 서비스*/
  @GetMapping("/notice/csnoticecustomer")
  public String csnoticecustomer(Model model, PageRequestDTO pageRequestDTO) {

    pageRequestDTO.setType_id(1);
    PageResponseDTO pageResponseDTO = csService.findAll(pageRequestDTO, 1);

    model.addAttribute(pageResponseDTO);

    return "/cs/notice/csnoticecustomer";
  }

  /*이벤트*/
  @GetMapping("/notice/csnoticeevent")
  public String csnoticeevent(Model model, PageRequestDTO pageRequestDTO) {

    pageRequestDTO.setType_id(2);
    PageResponseDTO pageResponseDTO = csService.findAll(pageRequestDTO, 2);

    model.addAttribute(pageResponseDTO);
    return "/cs/notice/csnoticeevent";
  }

  /*위해상품*/
  @GetMapping("/notice/csnoticehazard")
  public String csnoticehazard(Model model, PageRequestDTO pageRequestDTO) {

    pageRequestDTO.setType_id(4);
    PageResponseDTO pageResponseDTO = csService.findAll(pageRequestDTO, 4);

    model.addAttribute(pageResponseDTO);
    return "/cs/notice/csnoticehazard";
  }



  /*안전거래*/
  @GetMapping("/notice/csnoticesafe")
  public String csnoticesafe(Model model, PageRequestDTO pageRequestDTO) {

    pageRequestDTO.setType_id(4);
    PageResponseDTO pageResponseDTO = csService.findAll(pageRequestDTO, 4);

    model.addAttribute(pageResponseDTO);
    return "/cs/notice/csnoticesafe";
  }

  /*노티스 뷰*/
  @GetMapping("/notice/csnoticeview")
  public String csnoticeview(@RequestParam("id") int id, Model model) {

    //글 조회 서비스 호출
    NoticeDTO noticeDTO = csService.findById(id);
    model.addAttribute("noticeDTO", noticeDTO);

    String referer = request.getHeader("Referer");
    if (referer != null && referer.contains("csnoticeall")) {
      model.addAttribute("fromAll", true);
    } else {
      model.addAttribute("fromAll", false);
    }

    return "/cs/notice/csnoticeview";
  }


  /*faq 회원 리스트*/
  @GetMapping("/faq/csfaqmember")
  public String csfaqmember(Model model, PageRequestDTO pageRequestDTO) {

    // type_id별로 4번 호출해서 각각 리스트를 만듦
    PageResponseDTO joinFaqs = faqService.findAll(pageRequestDTO, 5);   // 가입
    PageResponseDTO leaveFaqs = faqService.findAll(pageRequestDTO, 6);  // 탈퇴
    PageResponseDTO infoFaqs = faqService.findAll(pageRequestDTO, 7);   // 회원정보
    PageResponseDTO loginFaqs = faqService.findAll(pageRequestDTO, 8);  // 로그인

    model.addAttribute("joinFaqs", joinFaqs.getDtoList());
    model.addAttribute("leaveFaqs", leaveFaqs.getDtoList());
    model.addAttribute("infoFaqs", infoFaqs.getDtoList());
    model.addAttribute("loginFaqs", loginFaqs.getDtoList());

    return "/cs/faq/csfaqmember";
  }

  /*faq 쿠폰 리스트*/
  @GetMapping("/faq/csfaqcoupon")
  public String csfaqcoupon(Model model, PageRequestDTO pageRequestDTO) {

    // type_id별로 4번 호출해서 각각 리스트를 만듦
    PageResponseDTO couponFaqs = faqService.findAll(pageRequestDTO, 9);
    PageResponseDTO pointFaqs = faqService.findAll(pageRequestDTO, 10);
    PageResponseDTO partnerFaqs = faqService.findAll(pageRequestDTO, 11);
    PageResponseDTO eventFaqs = faqService.findAll(pageRequestDTO, 12);

    model.addAttribute("couponFaqs", couponFaqs.getDtoList());
    model.addAttribute("pointFaqs", pointFaqs.getDtoList());
    model.addAttribute("partnerFaqs", partnerFaqs.getDtoList());
    model.addAttribute("eventFaqs", eventFaqs.getDtoList());

    return "/cs/faq/csfaqcoupon";
  }

  /*faq 주문/결제 리스트*/
  @GetMapping("/faq/csfaqorder")
  public String csfaqorder(Model model, PageRequestDTO pageRequestDTO) {

    PageResponseDTO productFaqs = faqService.findAll(pageRequestDTO, 13);
    PageResponseDTO paymentFaqs = faqService.findAll(pageRequestDTO, 14);
    PageResponseDTO historyFaqs = faqService.findAll(pageRequestDTO, 15);
    PageResponseDTO receiptFaqs = faqService.findAll(pageRequestDTO, 16);

    model.addAttribute("productFaqs", productFaqs.getDtoList());
    model.addAttribute("paymentFaqs", paymentFaqs.getDtoList());
    model.addAttribute("historyFaqs", historyFaqs.getDtoList());
    model.addAttribute("receiptFaqs", receiptFaqs.getDtoList());

    return "/cs/faq/csfaqorder";
  }

  /*faq 배송 리스트*/
  @GetMapping("/faq/csfaqdeliver")
  public String csfaqdeliver(Model model, PageRequestDTO pageRequestDTO) {

    PageResponseDTO statusFaqs = faqService.findAll(pageRequestDTO, 17);
    PageResponseDTO infoChangeFaqs = faqService.findAll(pageRequestDTO, 18);
    PageResponseDTO globalFaqs = faqService.findAll(pageRequestDTO, 19);
    PageResponseDTO sameDayFaqs = faqService.findAll(pageRequestDTO, 20);
    PageResponseDTO directGlobalFaqs = faqService.findAll(pageRequestDTO, 21);

    model.addAttribute("statusFaqs", statusFaqs.getDtoList());
    model.addAttribute("infoChangeFaqs", infoChangeFaqs.getDtoList());
    model.addAttribute("globalFaqs", globalFaqs.getDtoList());
    model.addAttribute("sameDayFaqs", sameDayFaqs.getDtoList());
    model.addAttribute("directGlobalFaqs", directGlobalFaqs.getDtoList());

    return "/cs/faq/csfaqdeliver";
  }

  /*faq 취소/반품/교환 리스트*/
  @GetMapping("/faq/csfaqcancel")
  public String csfaqcancel(Model model, PageRequestDTO pageRequestDTO) {

    PageResponseDTO returnReqFaqs = faqService.findAll(pageRequestDTO, 22);
    PageResponseDTO returnInfoFaqs = faqService.findAll(pageRequestDTO, 23);
    PageResponseDTO exchangeReqFaqs = faqService.findAll(pageRequestDTO, 24);
    PageResponseDTO exchangeInfoFaqs = faqService.findAll(pageRequestDTO, 25);
    PageResponseDTO cancelReqFaqs = faqService.findAll(pageRequestDTO, 26);
    PageResponseDTO refundFaqs = faqService.findAll(pageRequestDTO, 27);

    model.addAttribute("returnReqFaqs", returnReqFaqs.getDtoList());
    model.addAttribute("returnInfoFaqs", returnInfoFaqs.getDtoList());
    model.addAttribute("exchangeReqFaqs", exchangeReqFaqs.getDtoList());
    model.addAttribute("exchangeInfoFaqs", exchangeInfoFaqs.getDtoList());
    model.addAttribute("cancelReqFaqs", cancelReqFaqs.getDtoList());
    model.addAttribute("refundFaqs", refundFaqs.getDtoList());

    return "/cs/faq/csfaqcancel";
  }

  /*faq 여행/숙박/항공 리스트*/
  @GetMapping("/faq/csfaqtrip")
  public String csfaqtrip(Model model, PageRequestDTO pageRequestDTO) {

    PageResponseDTO travelFaqs = faqService.findAll(pageRequestDTO, 28);
    PageResponseDTO flightFaqs = faqService.findAll(pageRequestDTO, 29);

    model.addAttribute("travelFaqs", travelFaqs.getDtoList());
    model.addAttribute("flightFaqs", flightFaqs.getDtoList());

    return "/cs/faq/csfaqtrip";
  }

  /*faq 안전거래 리스트*/
  @GetMapping("/faq/csfaqsafe")
  public String csfaqsafe(Model model, PageRequestDTO pageRequestDTO) {

    PageResponseDTO ruleFaqs = faqService.findAll(pageRequestDTO, 30);
    PageResponseDTO ipFaqs = faqService.findAll(pageRequestDTO, 31);
    PageResponseDTO illegalFaqs = faqService.findAll(pageRequestDTO, 32);
    PageResponseDTO policyFaqs = faqService.findAll(pageRequestDTO, 33);
    PageResponseDTO dealFaqs = faqService.findAll(pageRequestDTO, 34);
    PageResponseDTO adFaqs = faqService.findAll(pageRequestDTO, 35);
    PageResponseDTO youthFaqs = faqService.findAll(pageRequestDTO, 36);

    model.addAttribute("ruleFaqs", ruleFaqs.getDtoList());
    model.addAttribute("ipFaqs", ipFaqs.getDtoList());
    model.addAttribute("illegalFaqs", illegalFaqs.getDtoList());
    model.addAttribute("policyFaqs", policyFaqs.getDtoList());
    model.addAttribute("dealFaqs", dealFaqs.getDtoList());
    model.addAttribute("adFaqs", adFaqs.getDtoList());
    model.addAttribute("youthFaqs", youthFaqs.getDtoList());

    return "/cs/faq/csfaqsafe";
  }

  /*faq 회원 글 보기*/
  @GetMapping("/faq/csfaqview")
  public String csfaqview(@RequestParam("id") int id, Model model) {

    // 글 조회 서비스 호출
    FaqDTO faqDTO = faqService.findById(id);
    model.addAttribute("faqDTO", faqDTO);

    return "/cs/faq/csfaqview";
  }


  /* qna 문의하기 글쓰기*/
  @GetMapping("/qna/csqnawrite")
  public String csqnawrite(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
    // 로그인 상태 체크
    if (userDetails == null) {
      return "redirect:/login";  // 로그인 페이지로 리디렉션
    }

    String username = userDetails.getUsername();
    model.addAttribute("member_id", username);

    return "/cs/qna/csqnawrite";
  }

  @PostMapping("/qna/csqnawrite")
  public String csqnawrite(QnaDTO qnaDTO) {
    int id = qnaService.qnaRegister(qnaDTO);

    int typeId = qnaDTO.getType_id(); // type_id 꺼냄

    if (typeId >= 5 && typeId <= 8) {
      return "redirect:/cs/qna/csqnamember"; // 회원 리스트
    } else if (typeId >= 9 && typeId <= 12) {
      return "redirect:/cs/qna/csqnacoupon"; // 쿠폰/혜택/이벤트 리스트
    } else if (typeId >= 13 && typeId <= 16) {
      return "redirect:/cs/qna/csqnaorder"; // 주문/결제 리스트
    } else if (typeId >= 17 && typeId <= 21) {
      return "redirect:/cs/qna/csqnadeliver"; // 배송 리스트
    } else if (typeId >= 22 && typeId <= 27) {
      return "redirect:/cs/qna/csqnacancel"; // 취소/반품/교환 리스트
    } else if (typeId >= 28 && typeId <= 29) {
      return "redirect:/cs/qna/csqnatrip"; // 여행/숙박/항공 리스트
    } else if (typeId >= 30 && typeId <= 36) {
      return "redirect:/cs/qna/csqnasafe"; // 안전거래 리스트
    } else {
      return "redirect:/cs/qna/csqnamember"; // fallback (예외처리)
    }
  }




  @GetMapping("/qna/csqnamember")
  public String csqnamember(Model model, PageRequestDTO pageRequestDTO) {

    pageRequestDTO.setName("회원");  // Set the filter for "name"

    // ✅ type_id는 전체니까 0 넘기기
    pageRequestDTO.setType_id(0);  // Ensure type_id is set to 0

    // Now calling the method that only takes PageRequestDTO and int
    PageResponseDTO pageResponseDTO = qnaService.findAll(pageRequestDTO, 0);

    model.addAttribute("pageResponseDTO", pageResponseDTO);

    return "/cs/qna/csqnamember";
  }

  @GetMapping("/qna/csqnacoupon")
  public String csqnacoupon(Model model, PageRequestDTO pageRequestDTO) {

    pageRequestDTO.setName("쿠폰");  // Set the filter for "name"

    // ✅ type_id는 전체니까 0 넘기기
    pageRequestDTO.setType_id(0);  // Ensure type_id is set to 0

    // Now calling the method that only takes PageRequestDTO and int
    PageResponseDTO pageResponseDTO = qnaService.findAll(pageRequestDTO, 0);

    model.addAttribute("pageResponseDTO", pageResponseDTO);

    return "/cs/qna/csqnacoupon";
  }

  @GetMapping("/qna/csqnaorder")
  public String csqnaorder(Model model, PageRequestDTO pageRequestDTO) {

    pageRequestDTO.setName("주문");  // Set the filter for "name"

    // ✅ type_id는 전체니까 0 넘기기
    pageRequestDTO.setType_id(0);  // Ensure type_id is set to 0

    // Now calling the method that only takes PageRequestDTO and int
    PageResponseDTO pageResponseDTO = qnaService.findAll(pageRequestDTO, 0);

    model.addAttribute("pageResponseDTO", pageResponseDTO);

    return "/cs/qna/csqnaorder";
  }

  @GetMapping("/qna/csqnadeliver")
  public String csqnadeliver(Model model, PageRequestDTO pageRequestDTO) {

    pageRequestDTO.setName("배송");  // Set the filter for "name"

    // ✅ type_id는 전체니까 0 넘기기
    pageRequestDTO.setType_id(0);  // Ensure type_id is set to 0

    // Now calling the method that only takes PageRequestDTO and int
    PageResponseDTO pageResponseDTO = qnaService.findAll(pageRequestDTO, 0);

    model.addAttribute("pageResponseDTO", pageResponseDTO);

    return "/cs/qna/csqnadeliver";
  }

  @GetMapping("/qna/csqnacancel")
  public String csqnacancel(Model model, PageRequestDTO pageRequestDTO) {

    pageRequestDTO.setName("취소");  // Set the filter for "name"

    // ✅ type_id는 전체니까 0 넘기기
    pageRequestDTO.setType_id(0);  // Ensure type_id is set to 0

    // Now calling the method that only takes PageRequestDTO and int
    PageResponseDTO pageResponseDTO = qnaService.findAll(pageRequestDTO, 0);

    model.addAttribute("pageResponseDTO", pageResponseDTO);

    return "/cs/qna/csqnacancel";
  }

  @GetMapping("/qna/csqnatrip")
  public String csqnatrip(Model model, PageRequestDTO pageRequestDTO) {

    pageRequestDTO.setName("여행");  // Set the filter for "name"

    // ✅ type_id는 전체니까 0 넘기기
    pageRequestDTO.setType_id(0);  // Ensure type_id is set to 0

    // Now calling the method that only takes PageRequestDTO and int
    PageResponseDTO pageResponseDTO = qnaService.findAll(pageRequestDTO, 0);

    model.addAttribute("pageResponseDTO", pageResponseDTO);

    return "/cs/qna/csqnatrip";
  }

  @GetMapping("/qna/csqnasafe")
  public String csqnasafe(Model model, PageRequestDTO pageRequestDTO) {

    pageRequestDTO.setName("안전");  // Set the filter for "name"

    // ✅ type_id는 전체니까 0 넘기기
    pageRequestDTO.setType_id(0);  // Ensure type_id is set to 0

    // Now calling the method that only takes PageRequestDTO and int
    PageResponseDTO pageResponseDTO = qnaService.findAll(pageRequestDTO, 0);

    model.addAttribute("pageResponseDTO", pageResponseDTO);

    return "/cs/qna/csqnasafe";
  }


  /*qna 회원 글 보기*/
  @GetMapping("/qna/csqnaview")
  public String csqnaview(@RequestParam("id") int id, Model model) {

    // 글 조회 서비스 호출
    QnaDTO qnaDTO = qnaService.findById(id);
    model.addAttribute("qnaDTO", qnaDTO);

    // 답글 조회
    ReplyDTO replyDTO = replyService.findByQnaId(id);
    model.addAttribute("replyDTO", replyDTO);

    return "/cs/qna/csqnaview";
  }



  @GetMapping("/cscontactview")
  public String cscontactview() {
    return "csqnaview";
  }




  @GetMapping("/csqnalist")
  public String csqnalist() {
    return "/cs/csqnalist";
  }


}
