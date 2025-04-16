/*
작성자: 이현민(id3ntity99)
내용: 데이터 등록을 위한 dialog(modal) 윈도우 팝업 스크립트
 */

import { initDialog } from "./modal.mjs";

$(() => {
  if ($(".registry-modal").length > 0) {
    initDialog(".registry-modal");

    $(".registry-modal-btn").click((e) => {
      $(".registry-modal").dialog("open");
    });
  }
});
