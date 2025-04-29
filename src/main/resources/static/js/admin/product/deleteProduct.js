$(() => {

  const stopAllBtn = $(".enroll-btn.delete-btn")
  const deleteBtn = $("#delete-btn")
  const statusBtn = $(".control-btn.status-btn")

  function requestWithData(method, url, data) {
    $.ajax(url, {
      type: method,
      dataType: "json",
      contentType: "application/json;utf-8",
      data: data,
      success: function (res) {
        alert("변경 완료");
        window.location.reload();
      },
      error: function (err) {
        alert("에러 발생")
      }
    })
  }

  // 개별 상태 변경 클릭 시
  statusBtn.click((event) => {
    console.log("status-btn clicked")
    const id = event.target.parentNode.parentNode.querySelector(
        ".product-id").innerText;
    const status = event.target.getAttribute("data-value")
    const url = `/api/admin/product?id=${id}&status=${status}`
    $.ajax(url, {
      type: "PUT",
      dataType: "json",
      success: function (res) {
        alert("변경 완료");
        window.location.reload();
      },
      error: function (err) {
        alert("에러 발생")
      }
    })
  })

  // 전체 판매중지 버튼 클릭 시
  stopAllBtn.click(() => {
    const checkboxes = $(".item-checkbox")
    const checkedBoxes = [];
    for (let box of checkboxes) {
      if (box.checked) {
        checkedBoxes.push(box);
      }
    }

    const prodIds = [];
    for (let box of checkedBoxes) {
      const id = box.parentNode.parentNode.querySelector(
          ".product-id").innerText;
      prodIds.push(id)
    }

    const data = JSON.stringify(prodIds)
    const status = event.target.getAttribute("data-value")
    const url = `/api/admin/product?status=${status}`
    requestWithData("PUT", url, data);
  })
})