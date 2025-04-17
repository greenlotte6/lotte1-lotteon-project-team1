import {
  initDialog,
  defaultOptions,
  initDialogWithOptions,
} from "/static/js/common/admin/modal.mjs";

function request(url) {
  //TODO 주문 상세 정보 불러오기
  alert(`TODO: ${url}로 GET 요청 보내기`);
}

$(() => {
  initDialog(".coupon-detail-modal");
  $(".coupon-detail").click((event) => {
    event.preventDefault();
    const url = event.target.href;
    $(".coupon-detail-modal").dialog("open", request(url));
  });

  //const newOptions = defaultOptions;
  //newOptions.height = 800;

  //initDialogWithOptions(".");
  //$(".enroll-btn.register-btn").click((event) => {});
});
