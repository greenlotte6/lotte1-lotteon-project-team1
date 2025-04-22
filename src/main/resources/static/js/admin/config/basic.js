$(() => {
  // 모든 form 데이터 자동 전송 끄기
  $(".post-form").submit((event) => {
    event.preventDefault();
  })

  $(".post-submit-btn").click((event) => {
    const parent = event.target.parentNode.parentElement;
    const form = parent.querySelector(".post-form")
    const inputs = parent.querySelectorAll("input[type=text]")
    let jsonStr = {}

    for (let input of inputs) {
      jsonStr[input.name] = input.value;
    }

    console.log(jsonStr)

    const url = form.action;

    $.ajax({
      type: "PUT",
      url: url,
      dataType: "json",
      contentType: "application/json;utf-8",
      data: JSON.stringify(jsonStr),
      success: function (res) {
        if (res.status === 302) {
          window.location.href = res.url;
        }
        console.log(res);
      },
      error: function (err) {
        console.log(err)
      }
    })
  });

  $(".post-submit-btn.multipart").click((event) => {
    console.log("멀티파트 데이터 전송하기")
  })
});
