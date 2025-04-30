$(() => {
  const submitBtn = $(".post-submit-btn");

  function getOptionsJson() {
    const optionFields = $(".option-fields");
    const jsonArray = [];
    for (let optionField of optionFields) {
      const productId = optionField.getAttribute("data-value-product");
      const optionId = optionField.getAttribute("data-value-option");
      const option = optionField.querySelector("input[name='option']").value
      const value = optionField.querySelector("input[name='value']").value
      const json = {
        "id": optionId,
        "product": {
          "id": productId
        },
        "option": option,
        "value": value
      }
      jsonArray.push(json)
    }
    console.log(jsonArray)
    return jsonArray;
  }

  function getProductJson() {
    const form = document.querySelector(".post-form")
    const formData = new FormData(form);
    const excluded = ["option", "value"]
    const filteredFormData = Array.from(formData.entries()).filter(
        ([key, _]) => !excluded.includes(key)) //name=option, value인 input 제외
    const json = Object.fromEntries(filteredFormData);//폼데이터를 JSON으로 변환
    return json
  }

  submitBtn.click((e) => {
    e.preventDefault();
    const optionsJson = getOptionsJson();
    const productJson = getProductJson();
    const json = {
      "options": optionsJson,
      "product": productJson
    }
    const url = $(".post-form").attr("action")

    $.ajax(url, {
      type: "PUT",
      contentType: "application/json;utf-8",
      data: JSON.stringify(json),
      success: function (data, status, xhr) {
        const redirectUrl = xhr.getResponseHeader("Location")
        if (redirectUrl) {
          console.log(redirectUrl)
        }
      },
      error: function (xhr) {
        console.log("Error: " + xhr.responseText)
      }
    })
  })
})