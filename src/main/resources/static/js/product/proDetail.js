document.addEventListener("DOMContentLoaded", function() {
    const btn = document.getElementById("detail-btn");
    const btnBox = document.getElementById("details-body");

    btn.addEventListener("click", () => {
    const isVisible = btnBox.style.display === "block";

    btnBox.style.display = isVisible ? "none" : "block";
    btn.textContent = isVisible ? "상세정보  더보기" : "접기";
    });
    

});
