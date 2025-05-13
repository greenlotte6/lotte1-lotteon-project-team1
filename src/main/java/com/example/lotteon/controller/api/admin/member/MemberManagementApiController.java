package com.example.lotteon.controller.api.admin.member;

import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.service.admin.point.PointService;
import com.example.lotteon.service.user.MemberService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/member")
public class MemberManagementApiController {

  private final PointService pointService;
  private final MemberService service;
  private final Gson gson;

  private ResponseEntity<String> ok() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("status", "ok");
    String jsonBody = gson.toJson(jsonObject);
    return ResponseEntity.ok().body(jsonBody);
  }

  private ResponseEntity<String> ok(String body) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("status", "ok");
    jsonObject.addProperty("body", body);
    String jsonBody = gson.toJson(jsonObject);
    return ResponseEntity.ok().body(jsonBody);
  }

  @GetMapping("/search")
  public ResponseEntity<MemberDTO> search(@RequestParam("id") String id) {
    MemberDTO member = service.getById(id);
    return ResponseEntity.ok(member);
  }

  /*
   * Member 테이블의 level 수정을 위한 컨트롤러 매핑
   */
  @PutMapping("/update")
  public ResponseEntity<String> updateMemberLevel(@RequestBody List<MemberDTO> updatedMember) {
    service.updateLevel(updatedMember);
    return ok();
  }

  @PutMapping("/edit")
  public ResponseEntity<String> editMember(@RequestBody MemberDTO member) {
    service.updateStatus(member);
    return ok();
  }

  @DeleteMapping("/point")
  public ResponseEntity<String> deleteMember(@RequestBody List<Integer> ids) {
    pointService.deleteMultipleById(ids);
    return ok();
  }
}
