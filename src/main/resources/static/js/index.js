$(() => {
  const searchInput = $("#search-input")
  const relatedKeywords = $("#related-keywords")

  function render(product) {
    const href = "/product/detail?id=" + product["id"]
    const productName = product["name"]
    const node = "<li class='keyword-row'>"
        + `<a href=${href}>${productName}</a>`
        + "</li>";
    relatedKeywords.append(node)
  }

  searchInput.off("keyup").on("keyup", (e) => {
    relatedKeywords.empty();
    if (relatedKeywords.hasClass("hidden")) {
      relatedKeywords.removeClass("hidden")
    }
    let keyword = e.target.value;
    $.ajax(`/search?keyword=${keyword}`, {
      type: "GET",
      dataType: "json",
      success: (products) => {
        for (let product of products) {
          render(product);
        }
      }
    })
  })
})