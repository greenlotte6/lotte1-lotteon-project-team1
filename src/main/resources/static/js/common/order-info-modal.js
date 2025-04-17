document.addEventListener("DOMContentLoaded", function() {
    const openBtn = document.getElementById('orderModal');
    const closeBtn = document.getElementById('close-modal');
    const modal = document.getElementById('order-info-modal');

    openBtn.addEventListener('click', function(e) {
      e.preventDefault(); 
      modal.style.display = 'flex';
    });

    closeBtn.addEventListener('click', function() {
      modal.style.display = 'none';
    });

    window.addEventListener('click', function(e) {
      if (e.target === modal) {
        modal.style.display = 'none';
      }
    });
});