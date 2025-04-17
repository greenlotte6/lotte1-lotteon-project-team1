document.addEventListener("DOMContentLoaded", function() {
    const openBtn = document.getElementById('sellerModal');
    const closeBtn = document.getElementById('close-modal');
    const modal = document.getElementById('seller-info-modal');
    
    openBtn.addEventListener('click', function(e) {
        e.preventDefault(); 
        modal.style.display = 'flex';
      });
  
      closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
      });
  
      // 모달 바깥 클릭 시 닫기
      window.addEventListener('click', function(e) {
        if (e.target === modal) {
          modal.style.display = 'none';
        }
      });
});