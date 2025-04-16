import { defaultOptions } from "/static/js/common/admin/modal.mjs";
import { initDialogWithOptions } from "/static/js/common/admin/modal.mjs";

function request(url) {
  //TODO 주문 상세 정보 불러오기
  alert(`TODO: ${url}로 GET 요청 보내기`);
}

$(() => {
  const options = defaultOptions;
  options.height = "800";
  options.draggable = true;
  options.resizable = true;
  initDialogWithOptions(".order-detail-modal", defaultOptions);
  $(".order-detail").click((event) => {
    event.preventDefault();
    const url = event.target.href;
    $(".order-detail-modal").dialog("open", request(url));
  });
});
