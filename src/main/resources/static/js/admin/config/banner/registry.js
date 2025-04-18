/**
 * 작성자: 이현민(id3ntity99)
 * 내용: 배너등록 버튼 클릭 시 등록할 배너에 대한 정보를 입력할 수 있는 dialog 표시
 */
$(() => {
  $(".registry-modal").dialog({
    width: 735,
    height: 560,
    autoOpen: false,
    draggable: false,
    resizable: false,
  });

  $(".control-btn.banner-registry-btn").on("click", (event) => {
    event.preventDefault();
    $(".registry-modal").dialog("open");
  });
});
