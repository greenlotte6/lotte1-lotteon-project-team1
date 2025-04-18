document.addEventListener('DOMContentLoaded', function (){
    const btnNext = document.querySelector('.agree-button');

    btnNext.addEventListener('click', function (e){
        const termsChecked1 = document.querySelector('input.ckbx1').checked;
        const termsChecked2 = document.querySelector('input.ckbx2').checked;
        const termsChecked3 = document.querySelector('input.ckbx3').checked;

        if (!termsChecked1 || !termsChecked2 || !termsChecked3) {
            e.preventDefault(); // 버튼 클릭 후 링크 이동 방지
            alert('약관 및 개인정보 처리방침에 모두 동의해야 다음으로 이동할 수 있습니다.');
        }
    });
});