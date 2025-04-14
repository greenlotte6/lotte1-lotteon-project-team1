const tabs = document.querySelectorAll(".terms-tabs li");
const title = document.querySelector(".terms-title");
const content = document.getElementById("terms-content");

const termsData = {
  buyer: {
    title: "구매회원 이용약관",
    content: `<p>제 1 조 (목적)<br>구매회원 약관 내용입니다.</p>
              <p>제 2 조<br>약관내용 제공</p>`
  },
  seller: {
    title: "판매회원 이용약관",
    content: `<p>판매회원 약관 내용입니다.</p>`
  },
  finance: {
    title: "전자금융거래 약관",
    content: `<p>전자금융거래 관련 내용입니다.</p>`
  },
  location: {
    title: "위치정보 이용약관",
    content: `<p>위치정보 제공 관련 약관입니다.</p>`
  },
  privacy: {
    title: "개인정보처리방침",
    content: `<p>개인정보 처리 및 보호에 대한 약관입니다.</p>`
  }
};

tabs.forEach(tab => {
  tab.addEventListener("click", () => {
    // 탭 하이라이트 변경
    tabs.forEach(t => t.classList.remove("active"));
    tab.classList.add("active");

    // 내용 변경
    const key = tab.getAttribute("data-tab");
    title.textContent = termsData[key].title;
    content.innerHTML = termsData[key].content;
  });
});
