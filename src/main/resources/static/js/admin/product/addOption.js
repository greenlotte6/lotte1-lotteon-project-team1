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
  const optionFields = $(".option-fields")
  const latestOption = optionFields[optionFields.length - 1].querySelector(
      ".option-input");
  const latestOptionName = latestOption.name;
  const latestOptionNum = parseInt(latestOptionName.match(/\d+/)) + 1;
  const randomId = getRandomInt();
  const tr = $("<tr>").addClass("option-fields")
  const thForOption = $("<th>").text(`옵션${latestOptionNum + 1}`);
  const thForValue = $("<th>").text(`옵션${latestOptionNum + 1} 항목`)
  const tdForOption = $("<td>")
  const tdForValue = $("<td>")
  const optionInput = $("<input>").attr({
    type: "text",
    class: "option-input",
    name: `options[${latestOptionNum}].option`,
  })
  const hiddenIdInput = $("<input>").attr({
    type: "hidden",
    name: `options[${latestOptionNum}].id`,
    value: randomId + 1
  })
  const valueInput = $("<input>").attr({
    type: "text",
    class: "option-value",
    name: `options[${latestOptionNum}].value`
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