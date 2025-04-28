$(() => {
  const deleteBtn = $(".enroll-btn.delete-btn")
  deleteBtn.click((event) => {
    const checkboxes = $(".seller-checkbox")
    const businessNums = [];
    for (let checkbox of checkboxes) {
      if (checkbox.checked) {
        businessNums.push(checkbox.value);
      }
    }
    const json = JSON.stringify(businessNums);

    $.ajax("/api/admin/seller/delete", {
      type: "DELETE",
      dataType: "json",
      contentType: "application/json;utf-8",
      data: json,
      success: function (res, xhr) {
        alert("수정 완료")
        location.reload();
      },
      error: function (err) {
        alert("에러 발생")
      }
    })
  })
})