$(() => {
  const uploadBtn = $(".post-submit-btn.upload");
  uploadBtn.click((event) => {
    const formData = new FormData();
    const inputs = $("input[type='file']")

    const metadata = {}

    for (let input of inputs) {
      formData.append("images", input.files[0]);
      if (input.files.length === 0) { //선택된 파일이 없는 경우
        continue;
      }
      formData.append("metadata", input.name)
      //metadata[input.name] = input.files[0].name; //{"value of input's name attr": "file name"}
    }

    console.log(metadata)
    formData.append("metadata", JSON.stringify(metadata));

    $.ajax({
      url: "/api/admin/config/basic/upload",
      processData: false,
      contentType: false,
      data: formData,
      type: "POST",
      success() {
        alert("전송 완료")
      }
    })

  })
})