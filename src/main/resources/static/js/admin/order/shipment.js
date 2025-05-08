/*
작성자: 이현민(id3ntity99)
내용: 주문목록 페이지의 "배송하기" 버튼 클릭시 jQuery UI Dialog를 보여주는 스크립트
*/
import {initDialog} from "../../common/admin/modal.mjs";

function request(url) {
  //TODO 주문 상세 정보 불러오기
  alert(`TODO: ${url}로 GET 요청 보내기`);
}

$(() => {
  initDialog(".shipment-modal");

  $(".ship-btn").click((event) => {
    const orderNumber = event.target.parentNode.parentNode.querySelector(
        ".order-detail").innerText;

    $.ajax(`/api/admin/order/ship?id=${orderNumber}`, {
      type: "GET",
      dataType: "json",
      success: function (response) {
        console.log(response)
        $(".shipment-modal table tr input[name='order.orderNumber']").val(
            response["order_number"]);
        $(".shipment-modal table tr input[name='order.recipientContact']").val(
            response["recipient_contact"]);
        $(".shipment-modal table tr input[name='order.recipientName']").val(
            response["recipient_name"]);
        $(".shipment-modal table tr input[name='order.recipientZip']").val(
            response["recipient_zip"]);
        $(".shipment-modal table tr input[name='order.recipientAddress']").val(
            response["recipient_address"]);
        $(".shipment-modal table tr input[name='order.recipientAddressDetail']").val(
            response["recipient_address_detail"]);

        $(".shipment-modal").dialog("open");
      }
    })

  });
});
