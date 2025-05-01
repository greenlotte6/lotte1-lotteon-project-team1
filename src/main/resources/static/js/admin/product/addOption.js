$(async () => {
  const addBtn = $(".add-option-btn")
  const targetElement = $(".options-table > tbody")
  let latestId = await request();

  addBtn.click((event) => {
    event.preventDefault();
    const createdRow = createNewOptionRow(++latestId);
    targetElement.append(createdRow)
  })
})

async function request() {
  const response = await fetch("/api/admin/product/options/id")
  const data = await response.json();
  return data["latestId"]
}

function getRandomInt() {
  return Math.floor(Math.random() * (20000 - 10000 + 1)) + 10000;
}

function createNewOptionRow(latestId) {
  //TODO 나중에 SELECT product_option.id FROM product_option ORDER BY product_option.id DESC LIMIT 1 으로 가장 최근에 추가된 옵션의 아이디값 가져오기
  const optionFields = $(".option-fields")
  const latestOptionName = optionFields[optionFields.length - 1].querySelector(
      ".option-input").name;
  const latestOptionNum = parseInt(latestOptionName.match(/\d+/)) + 1;
  const randomId = getRandomInt();
  const tr = $("<tr>").addClass("option-fields")
  const thForOption = $("<th>").text(`옵션${latestId}`);
  const thForValue = $("<th>").text(`옵션${latestId} 항목`)
  const tdForOption = $("<td>")
  const tdForValue = $("<td>")
  const optionInput = $("<input>").attr({
    type: "text",
    class: "option-input",
    name: `options[${latestId - 1}].option`,
  })
  const hiddenIdInput = $("<input>").attr({
    type: "hidden",
    name: `options[${latestId - 1}].id`,
    value: randomId + 1
  })
  const valueInput = $("<input>").attr({
    type: "text",
    class: "option-value",
    name: `options[${latestId - 1}].value`
  })
  tdForOption.append(optionInput)
  tdForOption.append(hiddenIdInput)
  tdForValue.append(valueInput)
  tr.append(thForOption)
  tr.append(tdForOption)
  tr.append(thForValue)
  tr.append(tdForValue)

  return tr;
}