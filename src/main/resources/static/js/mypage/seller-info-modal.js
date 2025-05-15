document.addEventListener("DOMContentLoaded", function () {
    const sellerButtons = document.querySelectorAll('.sellerModal');
    const sellerModal = document.getElementById('seller-info-modal');
    const sellerCloseBtn = document.getElementById('seller-close');

    const inquiryModal = document.getElementById('inquiry-info-modal');
    const inquiryCloseBtn = document.getElementById('inquiry-close');

    sellerModal.style.display = 'none';
    inquiryModal.style.display = 'none';

    // 문의 종류 매핑 (문자열 -> type_id)
    const typeMap = {
        "상품": 13,
        "배송": 17,
        "반품/취소": 22,
        "교환": 24,
        "기타": 27
    };

    // 판매자 모달 열기
    sellerButtons.forEach(function (btn) {
        btn.addEventListener('click', function (e) {
            e.preventDefault();
            const businessNumber = btn.getAttribute('data-id');

            fetch(`/mypage/wholeorder/seller/${businessNumber}`)
                .then(response => {
                    if (!response.ok) throw new Error('판매자 정보를 불러올 수 없습니다.');
                    return response.json();
                })
                .then(data => {
                    updateSellerModal(data);
                    sellerModal.style.display = 'flex';

                    // 문의하기 버튼 이벤트 다시 등록
                    const inquiryBtn = sellerModal.querySelector('.inquiryModal');
                    if (inquiryBtn) {
                        inquiryBtn.onclick = function () {
                            sellerModal.style.display = 'none';
                            inquiryModal.style.display = 'flex';

                            // 이메일은 로그인한 사용자 정보로 서버에서 처리하므로
                            // 클라이언트에선 입력받지 않아도 됨(필요시 초기화)
                            document.getElementById('inquiry-email').value = '';
                            clearInquiryForm();
                        };
                    }
                })
                .catch(error => alert(error.message));
        });
    });

    // 판매자 모달 닫기
    sellerCloseBtn.addEventListener('click', () => {
        sellerModal.style.display = 'none';
    });

    // 문의하기 모달 닫기
    inquiryCloseBtn.addEventListener('click', () => {
        inquiryModal.style.display = 'none';
    });

    // 문의하기 모달 바깥 클릭 시 닫기
    window.addEventListener('click', e => {
        if (e.target === inquiryModal) inquiryModal.style.display = 'none';
    });

    // 문의하기 폼 제출 이벤트
    const inquiryForm = inquiryModal.querySelector('form');
    inquiryForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const selectedType = inquiryForm.querySelector('input[name="type"]:checked');
        const typeText = selectedType ? selectedType.value : null;
        const type_id = typeMap[typeText];

        const title = document.getElementById('inquiry-title').value.trim();
        const content = document.getElementById('inquiry-content').value.trim();

        if (!type_id) {
            alert("문의 종류를 선택해주세요.");
            return;
        }
        if (!title) {
            alert("제목을 입력해주세요.");
            return;
        }
        if (!content) {
            alert("내용을 입력해주세요.");
            return;
        }

        // QnaDTO에 맞는 JSON 생성
        const qnaDTO = {
            title: title,
            content: content,
            type_id: type_id,
            // member_id, status는 서버에서 세팅하므로 제외
        };

        fetch('/mypage/wholeorder/qna', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(qnaDTO)
        })
            .then(response => {
                if (!response.ok) throw new Error("문의 등록 실패");
                return response.text();
            })
            .then(result => {
                alert("등록이 완료되었습니다.");
                inquiryModal.style.display = 'none';
                clearInquiryForm();
            })
            .catch(err => alert(err.message));
    });

    // 문의하기 모달 내 취소 버튼
    document.getElementById("close-btn").addEventListener('click', () => {
        inquiryModal.style.display = 'none';
        clearInquiryForm();
    });

    // 폼 초기화 함수
    function clearInquiryForm() {
        inquiryForm.reset();
        document.getElementById('inquiry-email').value = '';
    }

    // 판매자 모달 내용 채우기 함수
    function updateSellerModal(data) {
        const tds = document.querySelectorAll('.seller-Table .td-r');
        tds[0].innerText = '파워 딜러'; // 예시 등급
        tds[1].innerText = data.companyName || '';
        tds[2].innerText = data.ceo || '';
        tds[3].innerText = data.sellerNumber || '';
        tds[4].innerText = data.fax || '';
        tds[5].innerText = data.sellerId?.user?.email || '';
        tds[6].innerText = data.sellerId?.businessNumber || '';

        const user = data.sellerId?.user || {};
        const fullAddress = `[${user.zip || ''}] ${user.address || ''} ${user.addressDetail || ''}`;
        tds[7].innerText = fullAddress;
    }
});