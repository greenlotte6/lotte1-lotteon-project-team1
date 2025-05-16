document.addEventListener("DOMContentLoaded", function () {
    const openBtns = document.querySelectorAll(".orderModal");
    const closeBtn = document.getElementById("order-close");
    const modal = document.getElementById("order-info-modal");

    modal.style.display = "none";

    openBtns.forEach((btn) => {
        btn.addEventListener("click", function (e) {
            e.preventDefault();
            const orderNumber = btn.dataset.id;

            // API 호출해서 데이터 가져오기
            fetch(`/mypage/wholeorder/detail/${orderNumber}`)
                .then(response => response.json())
                .then(orderItems => {
                    if (!orderItems || orderItems.length === 0) {
                        alert("주문 정보를 불러오지 못했습니다.");
                        return;
                    }

                    const tbody = document.querySelector(".order-table");
                    tbody.querySelectorAll(".table-body").forEach(row => row.remove());

                    orderItems.forEach(item => {
                        const statusMap = {
                            1: "결제대기",
                            2: "결제완료",
                            3: "배송준비",
                            4: "배송중",
                            5: "배송완료",
                            6: "구매확정",
                            7: "취소요청",
                            8: "취소완료",
                            9: "환불요청",
                            10: "환불완료",
                            11: "교환요청",
                            12: "교환완료",
                        };
                        const discount = Math.round(item.product.price * (item.product.discountRate / 100));
                        const tr = document.createElement("tr");
                        tr.classList.add("table-body");
                        tr.innerHTML = `
                            <td class="order-date">${item.order.orderDate || ''}</td>
                            <td>
                                <div class="p-info">
                                    <div class="proimg">
                                        <img src="${item.product.image?.listThumbnailLocation || '/default.jpg'}" alt="이미지">
                                    </div>
                                    <div class="o-info">
                                        <p>주문번호 : <span class="order-number">${item.order.orderNumber}</span></p>
                                        <p>(주) <span class="seller-name">${item.product.seller.companyName}</span></p>
                                        <p class="product-name">${item.product.name}</p>
                                        <p>수량 : <span class="quantity">${item.amount}</span></p>
                                        <p class="price">${item.totalPrice}원</p>
                                    </div>
                                </div>
                            </td>
                            <td class="price-t">
                                <div class="p-info2">
                                    <div class="o-info2">
                                        <p>판매가 : <span class="price-original">${item.product.price}원</span></p>
                                        <p>할인 : <span class="price-discount">-${discount}원</span></p>
                                        <p>결제금액 : <span class="price-final price">${item.totalPrice}원</span></p>
                                    </div>
                                </div>
                            </td>
                            <td class="end order-status">${statusMap[item.order.status.id] || ''}</td>
                        `;
                        tbody.appendChild(tr);
                    });

                    // 배송정보는 첫 번째 상품 기준
                    const info = orderItems[0];
                    document.querySelector(".receiver-name").textContent = info.order.recipientName;
                    document.querySelector(".phone-number").textContent = info.order.recipientContact;
                    document.querySelector(".address").textContent = `${info.order.recipientAddress} ${info.order.recipientAddressDetail}`;
                    document.querySelector(".delivery-request").textContent = info.order.description || '';

                    modal.style.display = "flex";
                })
                .catch(err => {
                    console.error("주문정보 요청 실패", err);
                    alert("주문 정보를 불러오는 중 오류가 발생했습니다.");
                });
        });
    });

    closeBtn.addEventListener("click", function () {
        modal.style.display = "none";
    });

    window.addEventListener("click", function (e) {
        if (e.target === modal) {
            modal.style.display = "none";
        }
    });
});