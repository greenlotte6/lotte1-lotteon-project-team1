document.addEventListener("DOMContentLoaded", function () {
    const openBtns = document.querySelectorAll('.confirmModalBtn');  // 여러 개 버튼
    const closeBtns = document.querySelectorAll('.close-confirm');   // 닫기 버튼
    const modal = document.getElementById('confirm-info-modal');     // 모달창
    const confirmBtn = document.getElementById('confirm-order-btn'); // 모달 내 "확인" 버튼
    let currentOrderNumber = null; // 현재 선택된 주문번호 저장용

    modal.style.display = 'none';

    // 구매확정 버튼 눌렀을 때: 모달 열기
    openBtns.forEach(btn => {
        btn.addEventListener('click', function (e) {
            e.preventDefault();
            currentOrderNumber = btn.getAttribute('data-order-number'); // 주문번호 저장
            modal.style.display = 'flex';
        });
    });

    // 모달 닫기
    closeBtns.forEach(btn => {
        btn.addEventListener('click', function () {
            modal.style.display = 'none';
        });
    });

    // 모달 바깥 클릭 시 닫기
    window.addEventListener('click', function (e) {
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });

    // 모달 내 "구매확정" 버튼 눌렀을 때: 서버로 요청
    confirmBtn.addEventListener('click', function () {
        if (!currentOrderNumber) {
            alert("주문번호가 유효하지 않습니다.");
            return;
        }

        fetch('/mypage/wholeorder/confirm', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                //'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content // CSRF 토큰
            },
            body: JSON.stringify({ orderNumber: currentOrderNumber })
        })
            .then(response => {
                if (response.ok) {
                    alert("구매확정이 완료되었습니다.");
                    location.reload(); // 새로고침으로 반영
                } else {
                    alert("구매확정 처리에 실패했습니다.");
                }
            });

        modal.style.display = 'none';
    });
});