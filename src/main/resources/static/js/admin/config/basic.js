$(() => {
  // 모든 form 데이터 자동 전송 끄기
  $(".post-form").submit((event) => {
    event.preventDefault();
  })

  $(".post-submit-btn").click((event) => {
    const parent = event.target.parentNode.parentElement;
    const inputs = parent.querySelectorAll("input[type=text]")
    const topNodeKey = event.target.name;
    let parentJsonStr = {}
    let jsonStr = {}

    for (let input of inputs) {
      const key = input.name
      const value = input.value
      jsonStr[key] = value;
    }

    if (topNodeKey) {
      parentJsonStr[event.target.name] = jsonStr;
    } else {
      parentJsonStr = jsonStr;
    }

    console.log(parentJsonStr)

//    $.ajax({
//      type: "PUT",
//      url: "/api/admin/config/basic/",
//      dataType: "json",
//      contentType: "application/json;utf-8",
//      data: JSON.stringify(parentJsonStr),
//      success: function (res) {
//        if (res.status === 302) {
//          window.location.href = res.url;
//        }
//        console.log(res);
//      },
//      error: function (err) {
//        console.log(err)
//      }
//    })
  });

  $(".post-submit-btn.multipart").click((event) => {
    console.log("멀티파트 데이터 전송하기")
  })
});
