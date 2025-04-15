/*
작성자: 이현민(id3ntity99)
내용: 데이터 등록을 위한 dialog(modal) 윈도우 팝업 스크립트
 */
$(() => {
  $(".registry-modal").dialog({
    autoOpen: false,
    draggable: false,
    resizable: false,
    width: 860,
    height: 600,
  });

  $(".registry-modal-btn").click((e) => {
    $(".registry-modal").dialog("open");
  });
});
