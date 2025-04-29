$(() => {
  const deleteBtn = $(".enroll-btn.delete-btn");
  deleteBtn.click((event) => {
    const checkboxes = $(".item-checkbox");
    const checkedBoxes = [];

    for (let checkbox of checkboxes) {
      if (checkbox.checked) {
        checkedBoxes.push(checkbox)
      }
    }

    const pointIds = [];
    for (let checkedBox of checkedBoxes) {
      const currentUid = checkedBox.parentNode.parentNode.querySelector(
          ".point-id").innerText;
      console.log(currentUid)
      pointIds.push(currentUid);
    }

    const json = JSON.stringify(pointIds);

    $.ajax("/api/admin/member/point", {
      type: "DELETE",
      dataType: "json",
      contentType: "application/json;utf-8",
      data: json,
      success: function (res) {
        alert("삭제 완료")
        window.location.reload();
      },
      error: function (err) {
        alert("에러 발생")
        window.location.reload();
      }
    })
  })
})