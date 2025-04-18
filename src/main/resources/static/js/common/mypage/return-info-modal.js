document.addEventListener("DOMContentLoaded", function() {
    const openBtn = document.getElementById('returnModal');
    const closeBtns = document.querySelectorAll('.close-return'); // 👈 모두 선택
    const modal = document.getElementById('return-info-modal');
    
    openBtn.addEventListener('click', function(e) {
        e.preventDefault(); 
        modal.style.display = 'flex';
      });
  
      closeBtns.forEach(btn => {
        btn.addEventListener('click', function () {
          modal.style.display = 'none';
        });
      });
  
      // 모달 바깥 클릭 시 닫기
      window.addEventListener('click', function(e) {
        if (e.target === modal) {
          modal.style.display = 'none';
        }
      });
});