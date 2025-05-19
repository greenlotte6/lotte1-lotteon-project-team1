/*
    작성자 : 손준오(sjo112777)
    내용: 고객센터 문의하기 답변 컨트롤러
*/
package com.example.lotteon.controller.cs;

import com.example.lotteon.dto.cs.ReplyDTO;
import com.example.lotteon.repository.jpa.cs.ReplyRepository;
import com.example.lotteon.service.cs.QnaService;
import com.example.lotteon.service.cs.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReplyController {

  private final HttpServletRequest request;
  private final QnaService qnaService;
  private final ReplyRepository replyRepository;
  private final ReplyService replyService;


  /*문의하기 답변 쓰기*/
  @PostMapping("/admin/cs/qna/view")
  public String replywrite(@ModelAttribute ReplyDTO replyDTO) {

    int id = replyService.save(replyDTO);

    return "redirect:/admin/cs/qna/view?id=" + replyDTO.getQna_id();
  }


}
