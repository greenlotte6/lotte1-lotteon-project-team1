import {
  initDialog,
  defaultOptions,
  initDialogWithOptions,
} from "/js/common/admin/modal.mjs";

function request(id) {
  fetch(`/admin/coupon/issued/${id}`)
      .then(response => response.json())  // 응답을 JSON 형식으로 변환
      .then(couponHistory => {
          if (!couponHistory || !couponHistory.id) {
              alert("쿠폰 정보를 불러오지 못했습니다.");
              return;
          }

          const statusText = couponHistory.status === "unused" ? "미사용" :
              couponHistory.status === "used" ? "사용" : couponHistory.status;

          $(".coupon-id").text(couponHistory.coupon.id);
          $(".coupon-seller-id").text(couponHistory.coupon.seller_id);
          $(".coupon-publish-id").text(couponHistory.id);
          $(".coupon-used-id").text(statusText);
          $(".coupon-type-id").text(couponHistory.coupon.coupon_type.name);
          $(".coupon-user-id").text(couponHistory.user_id);
          $(".coupon-name-id").text(couponHistory.coupon.name);
          $(".coupon-benefit-id").text(couponHistory.coupon.coupon_benefit.benefit);
          $(".coupon-period").text(`${couponHistory.coupon.from} ~ ${couponHistory.coupon.to}`);
          $(".coupon-description").text(couponHistory.coupon.description);

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
