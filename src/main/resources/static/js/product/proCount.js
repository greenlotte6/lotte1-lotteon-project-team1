document.addEventListener("DOMContentLoaded", function () {
  const minusBtn = document.querySelector('.minus');
  const plusBtn = document.querySelector('.plus');
  const countInput = document.querySelector('.count-input');
  const countInputHidden = $(".count-input-hidden")
  const priceDisplay = document.getElementById('sum_price');
  const totalPriceDisplay = document.querySelector('#total-price');

  // 초기값 설정
  const unitPrice = priceDisplay.innerText;

  // 💡 가격 업데이트
  function updatePrice() {
    const count = parseInt(countInput.value); // html파일에 텍스트로 되어있으므로 int로 변환
    const total = unitPrice * count;

    // 가격 포맷팅: 1000단위 콤마 + 원 붙이기
    const formattedPrice = total.toLocaleString() + "원";

    priceDisplay.textContent = formattedPrice;
    totalPriceDisplay.value = formattedPrice;
  }

  minusBtn.addEventListener('click', (event) => {
    event.preventDefault();
    let current = parseInt(countInput.value);
    if (current > 1) {
      countInput.value = current - 1;
      countInputHidden.val(current - 1);
      updatePrice(); //  수량 줄일 때 가격 갱신
    }
  });

  plusBtn.addEventListener('click', (event) => {
    event.preventDefault();
    let current = parseInt(countInput.value);
    countInput.value = current + 1;
    countInputHidden.val(current + 1);
    updatePrice(); //  수량 늘릴 때 가격 갱신
  });

  // 수동으로 input 변경될 때도 가격 업데이트
  countInput.addEventListener('change', () => {
    if (countInput.value < 1) {
      countInput.value = 1;
      countInput.value = 1;
    } // 최소 1 보장
    updatePrice();
  });

  // 초기 가격 설정
  updatePrice();
});
