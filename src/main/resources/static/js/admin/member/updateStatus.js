$(() => {
  function request(method, data) {
    $.ajax("/api/admin/member/edit", {
      type: method,
      dataType: "json",
      contentType: "application/json;utf-8",
      data: data,
      success: function (res) {
        alert("수정 완료")
        window.location.reload();
      },
      error: function (err) {
        alert("에러 발생")
      }
    })
  }

  function getUserId(event) {
  }

  const statusBtn = $(".status-btn")
  statusBtn.click((event) => {
    const json = {}
    const userJson = {}
    const memberIdJson = {}
    const newStatus = event.target.getAttribute("data-value")
    const userId = event.target.parentNode.parentNode.querySelector(
        ".member-id").innerText;
    userJson["id"] = userId;
    memberIdJson["user"] = userJson;
    json["memberId"] = memberIdJson;
    json["status"] = newStatus;

    request("PUT", JSON.stringify(json));
  })

  const destroyBtn = $(".destroy-btn")
})