/**
 * 작성자: 이현민(id3ntity99)
 * 내용: 배너등록 버튼 클릭 시 등록할 배너에 대한 정보를 입력할 수 있는 dialog 표시
 */
$(() => {
  $(".banner-registry").dialog({
    width: 735,
    height: 590,
    autoOpen: false,
    draggable: false,
  });

  $(".register-btn").on("click", () => {
    $(".banner-registry").dialog("open");
  });
});
