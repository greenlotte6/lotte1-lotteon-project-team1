$(() => {
  function selectAll() {
    const checkboxes = $(".seller-checkbox");
    for (let checkbox of checkboxes) {
      checkbox.checked = true;
    }
  }

  function unselectAll() {
    const checkboxes = $(".seller-checkbox");
    for (let checkbox of checkboxes) {
      checkbox.checked = false;
    }
  }

  let allSelected = false;
  $(".select-all").click((event) => {
    if (!allSelected) {
      selectAll()
      allSelected = true;
    } else {
      unselectAll()
      allSelected = false;
    }
  })
})