document.addEventListener("DOMContentLoaded", function() {
    const minusBtn = document.querySelector('.minus');
    const plusBtn = document.querySelector('.plus');
    const countInput = document.querySelector('.count-input');

    minusBtn.addEventListener('click', (event) => {
        event.preventDefault(); // 기본 폼 제출 방지
        let current = parseInt(countInput.value);
        if (current > 1) {
            countInput.value = current - 1;
        }
    });

    plusBtn.addEventListener('click', (event) => {
        event.preventDefault(); // 기본 폼 제출 방지
        let current = parseInt(countInput.value);
        countInput.value = current + 1;
    });
});
  