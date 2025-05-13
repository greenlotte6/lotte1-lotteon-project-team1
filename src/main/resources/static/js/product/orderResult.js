$(() => {
  const trs = $(".table_bd")
  let priceResult = 0;
  let deliveryFeeResult = 0;
  let discountResult = 0;
  let totalPriceResult = 0;
  for (let tr of trs) {
    const price = parseInt(tr.querySelector(".product-price").innerText);
    priceResult += price;

    const deliveryFee = parseInt(tr.querySelector(".delivery-fee").innerText);
    deliveryFeeResult += deliveryFee;

    const discount = parseInt(tr.querySelector(".discount").innerText)
    discountResult += discount;

    const sumPrice = parseInt(tr.querySelector(".sumprice").innerText)
    totalPriceResult += sumPrice;
  }

  $(".price-result").text(priceResult)
  $("#discount-result").text(discountResult)
  $("#delivery-fee-result").text(deliveryFeeResult)
  $("#total-price-result").text(totalPriceResult)
})