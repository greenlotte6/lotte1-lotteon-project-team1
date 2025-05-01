import {
  initDialog,
  defaultOptions,
  initDialogWithOptions,
} from "/js/common/admin/modal.mjs";

function request(id) {
  fetch(`/admin/coupon/coupon/${id}`)
      .then(response => response.json())  // 응답을 JSON 형식으로 변환
      .then(coupon => {
        if (!coupon || !coupon.id) {
          alert("쿠폰 정보를 불러오지 못했습니다.");
          return;
        }


        // 모달 내부 DOM 요소에 값 삽입
        $(".coupon-id").text(coupon.id);
        $(".coupon-seller-id").text(coupon.seller_id);
        $(".coupon-type-id").text(coupon.coupon_type.name); // type_id 대신 name 사용
        $(".coupon-name-id").text(coupon.name);
        $(".coupon-benefit-id").text(coupon.coupon_benefit.benefit);
        $(".coupon-period").text(`${coupon.from} ~ ${coupon.to}`);
        $(".coupon-description").text(coupon.description);

        // 모달 열기
        $(".coupon-detail-modal").dialog("open");
      })
      .catch(error => {
        alert("쿠폰 정보를 불러오는 중 오류 발생");
        console.error(error);
      });
}

$(() => {
  initDialog(".coupon-detail-modal");

  $(".coupon-detail").click((event) => {
    event.preventDefault();
    const couponId = $(event.currentTarget).data("id");
    request(couponId);
  });


  //const newOptions = defaultOptions;
  //newOptions.height = 800;

  //initDialogWithOptions(".");
  //$(".enroll-btn.register-btn").click((event) => {});
});
