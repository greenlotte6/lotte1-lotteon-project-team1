import {initDialog} from "../../common/admin/modal.mjs";

function renderProductInfo(entryPoint, response) {
  const orderItems = response["orderItems"];

  const headers = `
    <tr>
      <th></th>
      <th>상품번호</th>
      <th>상품명</th>
      <th>판매자</th>
      <th>가격</th>
      <th>할인</th>
      <th>수량</th>
      <th>배송비</th>
      <th>결제금액</th>
    </tr>`
  entryPoint.append(headers);

  $.each(orderItems, function (index, json) {
    const price = json["product"]["price"]
    const dc = json["product"]["discountRate"]
    const amount = json["amount"]
    const deliFee = json["product"]["deliveryFee"];
    const content = `
    <tr>
      <td>
        <img src="${json['product']['image']['listThumbnailLocation']}" />
      </td>
      <td>
        ${json['order']['orderNumber']}
      </td>
      <td>
        ${json['product']['name']}
      </td>
      <td>
        ${json['product']['seller']['companyName']}
      </td>
      <td>
        ${price}
      </td>
      <td>
        ${dc}
      </td>
      <td>
         ${amount}
      </td>
      <td>
         ${deliFee}
      </td>
      <td>
        ${price - (price * dc / 100) * amount + deliFee}
      </td>
    </tr>`
    entryPoint.append(content);
  })
}

function renderDeliveryInfo(entryPoint, response) {
  let desc;

  const descExists = response["delivery"]["order"].hasOwnProperty("description")

  if (descExists) {
    desc = response["delivery"]["order"]["description"]
  } else {
    desc = "-";
  }

  const content = `
          <tr id="delivery-iter-point">
            <th>주문번호</th>
            <td>${response["delivery"]["order"]["orderNumber"]}</td>
          </tr>
          <tr>
            <th>수령인</th>
            <td>${response["delivery"]["order"]["recipientName"]}</td>
          </tr>
          <tr>
            <th>연락처</th>
            <td>${response["delivery"]["order"]["recipientContact"]}-1234-1234</td>
          </tr>
          <tr>
            <th>배송지 주소</th>
            <td>${response["delivery"]["order"]["recipientAddress"]}</td>
          </tr>
          <tr>
            <th>배송지 상세주소</th>
            <td>${response["delivery"]["order"]["recipientAddressDetail"]}</td>
          </tr>
          <tr>
            <th>택배사</th>
            <td>${response["delivery"]["deliveryCompany"]["companyName"]}</td>
          </tr>
          <tr>
            <th>송장번호</th>
            <td>${response["delivery"]["deliveryNumber"]}</td>
          </tr>
          <tr>
            <th>기타</th>
            <td>${desc}</td>
          </tr>
  `;
  entryPoint.append(content)
}

$(() => {
  const productInfoEntryPoint = $("#product-iter-point")
  const deliveryInfoEntryPoint = $("#delivery-iter-point")
  initDialog(".delivery-detail-modal");
  $(".delivery-detail").click((event) => {
    productInfoEntryPoint.empty();
    deliveryInfoEntryPoint.empty();
    event.preventDefault();
    const url = event.target.href;

    $.ajax(url, {
      type: "GET",
      dataType: "json",
      success: function (response) {
        console.log(response)
        renderProductInfo(productInfoEntryPoint, response);
        renderDeliveryInfo(deliveryInfoEntryPoint, response)
        $(".delivery-detail-modal").dialog("open");
      }
    })

  });
});
