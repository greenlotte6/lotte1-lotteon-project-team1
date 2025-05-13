$(() => {
  let price = 0;
  let discountedPrice = 0;
  let deliveryFee = 0;
  let totalPrice = 0;
  let totalPoint = 0;
  let totalAmount = 0;

  const trs = $(".table_bd")

  for (let tr of trs) {
    price += parseInt(tr.querySelector(".price-td").innerText)
    const currentDiscountRate = parseInt(
        tr.querySelector(".discount-rate").innerText);
    const amount = parseInt(tr.querySelector(".amount").innerText);
    totalAmount += amount;
    discountedPrice += price * (currentDiscountRate / 100);
    deliveryFee += parseInt(tr.querySelector(".delivery-fee").innerText);
    totalPoint += parseInt(tr.querySelector(".point").innerText);
    totalPrice += (price - discountedPrice) * amount + deliveryFee;
  }

  console.log(price, discountedPrice, deliveryFee, totalPrice, totalAmount,
      totalPoint)
  $("#total-amount-result").text(totalAmount)
  $("#price-result").text(price);
  $("#total-discounted-result").text(discountedPrice)
  $("#total-delivery-fee").text(deliveryFee)
  $("#total-price-result").text(totalPrice)
  $("#total-point-result").text(totalPoint)
})