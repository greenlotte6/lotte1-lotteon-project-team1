function request(businessNumber, newStatus) {
  $.ajax(
      `/api/admin/seller/manage?businessNumber=${businessNumber}&status=${newStatus}`,
      {
        type: "PUT",
        success: function (res) {
          alert("수정 완료")
          location.reload();
        },
        error: function (err) {
          alert("에러 발생")
        }
      })
}

$(() => {
  const buttons = $(".manage-btn");
  buttons.click((event) => {
    const businessNumber = event.target.parentNode.parentNode.querySelector(
        "#business-number").innerText;
    const newStatus = event.target.name;
    request(businessNumber, newStatus);
  })
})