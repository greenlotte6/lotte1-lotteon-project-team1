/*
작성자: 이현민(id3ntity99)
내용: 주문목록 페이지의 "배송하기" 버튼 클릭시 jQuery UI Dialog를 보여주는 스크립트
*/
import { defaultOptions } from "/static/js/common/admin/modal.mjs";
import { initDialog } from "/static/js/common/admin/modal.mjs";

function request(url) {
  //TODO 주문 상세 정보 불러오기
  alert(`TODO: ${url}로 GET 요청 보내기`);
}

$(() => {
  initDialog(".shipment-modal");

  $(".ship-btn").click((event) => {
    //TODO: 클릭된 "배송하기" 버튼이 속한 <tr>에서 주문번호 가져오기 & 가져온 주문번호로 서버에서 배송정보 가져오기
    $(".shipment-modal").dialog("open");
  });
});
