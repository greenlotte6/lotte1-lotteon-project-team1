import {
  defaultOptions,
  initDialogWithOptions
} from "../../common/admin/modal.mjs";

function filterOrderStatus(statusId) {
  switch (statusId) {
    case 1: {
      return "결제대기"
    }
    case 2: {
      return "결제완료"
    }
    case 3: {
      return "배송준비"
    }
    case 4: {
      return "배송중"
    }
    case 5: {
      return "배송완료"
    }
    case 6: {
      return "구매확정"
    }
    case 7: {
      return "취소요청"
    }
    case 8: {
      return "취소완료"
    }
    case 9: {
      return "환불요청"
    }
    case 10: {
      return "환불완료"
    }
    case 11: {
      return "교환요청"
    }
    case 12: {
      return "교환완료"
    }
  }
}

$(() => {
  const options = defaultOptions;
  options.height = "800";
  options.draggable = true;
  options.resizable = true;
  initDialogWithOptions(".order-detail-modal", defaultOptions);

  const orderNumberField = $(".order-number")
  const orderPaymentField = $(".order-payment")
  const orderMemberNameAndContactField = $(".order-member-name-contact")
  const orderStatusField = $(".order-status")
  const orderTotalPrice = $(".order-info-total-price")
  const recipientNameField = $(".recipient-name")
  const recipientContactField = $(".recipient-contact")
  const recipientAddressField = $(".recipient-address")
  const recipientAddressDetailField = $(".recipient-address-detail")

  $(".order-detail").click((event) => {
    event.preventDefault();
    const url = event.target.href;

    $.ajax(url, {
      type: "GET",
      dataType: "json",
      success: function (response) {
        const iterationPoint = $("#iteration-point")// <tbody></tbody>
        iterationPoint.empty();
        let totalProductPrice = 0;
        let totalDiscountedPrice = 0;
        let totalDeliveryFee = 0;
        let totalPriceToPay = 0;

        $.each(response, function (index, json) {
          const tr = $("<tr></tr>");

          const td = $("<td></td>")
          const productImageTd = $("<img />");
          productImageTd.attr("src",
              json["product"]["image"]["listThumbnailLocation"]);
          td.append(productImageTd)
          tr.append(td)

          const orderNumberTd = $("<td></td>")
          orderNumberTd.text(json["order"]["orderNumber"])
          tr.append(orderNumberTd)

          const productNameTd = $("<td></td>")
          productNameTd.text(json["product"]["name"])
          tr.append(productNameTd)

          const sellerNameTd = $("<td></td>")
          sellerNameTd.text(json["product"]["seller"]["companyName"])
          tr.append(sellerNameTd)

          const priceTd = $("<td></td>")
          const price = json["product"]["price"];
          totalProductPrice += price;
          priceTd.text(price)
          tr.append(priceTd)

          const priceToDiscountTd = $("<td></td>")
          const discountRate = json["product"]["discountRate"]
          const priceToDiscount = price * (discountRate / 100)
          totalDiscountedPrice += priceToDiscount;
          priceToDiscountTd.text(`-${priceToDiscount}`)
          tr.append(priceToDiscountTd)

          const amountTd = $("<td></td>")
          const amount = json["amount"]
          amountTd.text(amount);
          tr.append(amountTd)

          const deliveryFeeTd = $("<td></td>")
          const deliveryFee = json["product"]["deliveryFee"];
          totalDeliveryFee += deliveryFee;
          deliveryFeeTd.text(deliveryFee)
          tr.append(deliveryFeeTd)

          const totalPriceTd = $("<td></td>")
          const totalPrice = (price - priceToDiscount) * amount + deliveryFee;
          totalPriceTd.text(totalPrice)
          totalPriceToPay += totalPrice;
          tr.append(totalPriceTd)

          iterationPoint.append(tr);
        })
        $("#total-product-price").text(totalProductPrice)
        $("#total-discounted-price").text(`-${totalDiscountedPrice}`)
        $("#total-delivery-fee").text(totalDeliveryFee)
        $("#total-price").text(totalPriceToPay)

        const orderNumber = response[0]["order"]["orderNumber"];
        orderNumberField.text(orderNumber)

        const payment = response[0]["order"]["payment"]
        orderPaymentField.text(payment)

        const memberName = response[0]["order"]["member"]["name"]
        const memberContact = response[0]["order"]["member"]["memberId"]["user"]["contact"]
        orderMemberNameAndContactField.text(`${memberName}/${memberContact}`)

        const orderStatus = response[0]["order"]["status"]["id"]
        const statusString = filterOrderStatus(orderStatus);
        orderStatusField.text(statusString)

        orderTotalPrice.text(totalPriceToPay);

        const recipientName = response[0]["order"]["recipientName"]
        recipientNameField.text(recipientName);

        const recipientContact = response[0]["order"]["recipientContact"]
        recipientContactField.text(recipientContact)

        const recipientAddress = response[0]["order"]["recipientAddress"]
        recipientAddressField.text(recipientAddress)

        const recipientAddressDetail = response[0]["order"]["recipientAddressDetail"]
        recipientAddressDetailField.text(recipientAddressDetail);
      }
    })

    $(".order-detail-modal").dialog("open");
  });
});
