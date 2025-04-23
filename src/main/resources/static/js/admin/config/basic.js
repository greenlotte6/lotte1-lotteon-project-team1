$(() => {
  function beforeRequest(xhr, url) {
    console.log(`Initiating request to ${url}`)
  }

  function onRequestSuccess(res) {
    alert("수정 완료")
  }

  function onRequestError(err) {
    alert(err);
  }

  function request(url, json) {
    $.ajax({
      type: "PUT",
      url: url,
      dataType: "json",
      contentType: "application/json;utf-8",
      data: JSON.stringify(json),
      beforeSend: function (xhr) {
        beforeRequest(xhr, url)
      },
      success: function (res) {
        onRequestSuccess(res);
      },
      error: function (err) {
        onRequestError(err)
      }
    })
  }

  // 모든 form 데이터 자동 전송 끄기
  $(".post-form").submit((event) => {
    event.preventDefault();
  })

  $(".post-submit-btn").click((event) => {
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

  $(".post-submit-btn.multipart").click((event) => {
    console.log("멀티파트 데이터 전송하기")
  })
});
