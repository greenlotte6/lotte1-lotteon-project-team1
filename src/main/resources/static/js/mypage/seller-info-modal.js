document.addEventListener("DOMContentLoaded", function () {
    // 모달 1
    const openBtn = document.querySelector('.sellerModal');   // ← class로 수정
    const closeBtn = document.getElementById('seller-close');
    const modal = document.getElementById('seller-info-modal');

    // 모달 2
    const openBtn2 = document.querySelector('.inquiryModal'); // ← class로 수정
    const closeBtn2 = document.getElementById('inquiry-close');
    const modal2 = document.getElementById('inquiry-info-modal');

    modal.style.display = 'none';
    modal2.style.display = 'none';

    function hideModal1() {
        modal.style.display = 'none';
    }

    openBtn.addEventListener('click', function (e) {
        e.preventDefault();
        hideModal1();
        modal.style.display = 'flex';
    });

    closeBtn.addEventListener('click', function () {
        modal.style.display = 'none';
    });

    openBtn2.addEventListener('click', function () {
        hideModal1();
        modal2.style.display = 'flex';
    });

    closeBtn2.addEventListener('click', function () {
        modal2.style.display = 'none';
    });

    window.addEventListener('click', function (e) {
        if (e.target === modal2) {
            modal2.style.display = 'none';
        }
    });

    const submitBtn = document.getElementById("submit-btn");
    submitBtn.addEventListener('click', function (e) {
        alert("등록이 완료되었습니다.");
        modal2.style.display = 'none';
    });

    const closebtn = document.getElementById("close-btn");
    closebtn.addEventListener('click', function () {
        modal2.style.display = 'none';
    });
});