document.addEventListener("DOMContentLoaded", function() {
    const openBtns = document.querySelectorAll('.exchangeModal'); // ✅ class로 수정 (여러 개)
    const closeBtns = document.querySelectorAll('.close-exchange');
    const modal = document.getElementById('exchange-info-modal');
    const form = modal.querySelector("form[name='formRegister']");
    const submitBtn = form.querySelector(".inquiry button[type='submit'], .inquiry button:not([type])"); // 반품신청 버튼

    modal.style.display = 'none';

    // ✅ 여러 개의 버튼에 각각 이벤트 등록
    openBtns.forEach((btn) => {
        btn.addEventListener("click", function (e) {
            e.preventDefault();
            const url = btn.dataset.url;

            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error("서버 응답 오류");
                    }
                    return response.json();
                })
                .then(data => {
                    if (!data || data.length === 0) {
                        alert("주문 정보를 찾을 수 없습니다.");
                        return;
                    }

                    const item = data[0];

                    // 할인 계산
                    const price = item.product.price || 0;
                    const discountRate = item.product.discountRate || 0;
                    const discount = Math.round(price * (discountRate / 100));

                    // 날짜, 주문번호
                    modal.querySelector(".order-date").textContent = item.order.orderDate || '';
                    modal.querySelector(".order-number").textContent = item.order.orderNumber || '';

                    // 📌 숨겨진 input에 주문번호 세팅
                    modal.querySelector(".order-number-input").value = item.order.orderNumber || '';

                    // 판매자, 상품 정보
                    modal.querySelector(".seller-name").textContent = item.product.seller.companyName || '';
                    modal.querySelector(".product-name").textContent = item.product.name || '';
                    modal.querySelector(".quantity").textContent = item.amount + "개";

                    // 가격 정보
                    modal.querySelectorAll(".price").forEach(p => {
                        p.textContent = item.totalPrice.toLocaleString() + "원";
                    });
                    modal.querySelector(".price-original").textContent = price.toLocaleString() + "원";
                    modal.querySelector(".price-discount").textContent = "-" + discount.toLocaleString() + "원";

                    // 이미지 처리
                    const imgTag = modal.querySelector(".table-proimg img");
                    imgTag.src = item.product.image?.listThumbnailLocation || "/default.jpg";

                    // 주문 상태
                    const statusMap = {
                        1: "결제대기", 2: "결제완료", 3: "배송준비", 4: "배송중",
                        5: "배송완료", 6: "구매확정", 7: "취소요청", 8: "취소완료",
                        9: "환불요청", 10: "환불완료", 11: "교환요청", 12: "교환완료",
                    };
                    modal.querySelector(".order-status").textContent = statusMap[item.order.status.id] || '';

                    modal.style.display = "flex";
                })
                .catch(error => {
                    alert("주문 정보를 불러오는 중 오류가 발생했습니다.");
                    console.error(error);
                });
        });
    });

    // 모달 닫기 버튼
    closeBtns.forEach((btn) => {
        btn.addEventListener("click", function () {
            modal.style.display = "none";
        });
    });

    // 모달 외부 클릭 시 닫기
    window.addEventListener("click", function (e) {
        if (e.target === modal) {
            modal.style.display = "none";
        }
    });

    // ✅ 반품신청 버튼 클릭 시 form 제출
    submitBtn.addEventListener("click", function (e) {
        e.preventDefault(); // 기본 제출 방지 후 validation 등 가능

        const orderNumber = form.querySelector(".order-number-input").value;
        const reasonChecked = form.querySelector("input[name='reason_id']:checked");
        const description = form.querySelector("textarea[name='description']").value.trim();

        // 간단한 유효성 검사
        if (!reasonChecked) {
            alert("반품유형을 선택해주세요.");
            return;
        }

        if (description === "") {
            alert("반품 사유를 입력해주세요.");
            return;
        }

        form.submit(); // 유효성 통과 시 폼 제출
    });
});