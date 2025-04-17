import { initDialog } from "/static/js/common/admin/modal.mjs";

function request(url) {
  //TODO 주문 상세 정보 불러오기
  alert(`TODO: ${url}로 GET 요청 보내기`);
}

$(() => {
  initDialog(".delivery-detail-modal");
  $(".delivery-detail").click((event) => {
    event.preventDefault();
    const url = event.target.href;
    $(".delivery-detail-modal").dialog("open", request(url));
  });
});
