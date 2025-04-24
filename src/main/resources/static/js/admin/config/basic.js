$(() => {
  function request(url, json) {
    $.ajax({
      type: "PUT",
      url: url,
      dataType: "application/json",
      contentType: "application/json;utf-8",
      data: JSON.stringify(json),
      beforeSend: function (xhr) {
        console.log(`Initiating request to ${url}`)
      },
      success: function (res) {
        alert("수정 완료")
      },
      error: function (err) {
        alert("에러 발생")
      }
    })
  }

  // 모든 form 데이터 자동 전송 끄기
  $(".post-form").submit((event) => {
    event.preventDefault();
  })

  $(".post-submit-btn.update-btn").click((event) => {
    const key = event.target.name;
    const parent = event.target.parentNode.parentElement;
    const form = parent.querySelector(".post-form")
    const inputs = parent.querySelectorAll("input[type=text]")
    let json = {}

    for (let input of inputs) {
      json[input.name] = input.value;
    }

    const url = form.action + `?key=${key}`;

    request(url, json);

  });
});
