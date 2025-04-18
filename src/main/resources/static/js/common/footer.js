$(function () {
  const familySiteDropBtn = $(".footer-drop-btn.family-site");
  const serviceDropBtn = $(".footer-drop-btn.seller-service");
  const familySiteSelectors = $(".footer-selectors.family-site");
  const serviceSelectors = $(".footer-selectors.seller-service");

  let isFamilySiteDropped = false;
  familySiteDropBtn.on("click", (event) => {
    if (isFamilySiteDropped) {
      isFamilySiteDropped = false;
      familySiteSelectors.css("display", "none");
    } else {
      isFamilySiteDropped = true;
      familySiteSelectors.css("display", "block");
    }
  });

  let isServiceDropped = false;
  serviceDropBtn.on("click", () => {
    if (isServiceDropped) {
      isServiceDropped = false;
      serviceSelectors.css("display", "none");
    } else {
      isServiceDropped = true;
      serviceSelectors.css("display", "block");
    }
  });

  $("#dialog")
  .dialog({
    autoOpen: false,
    draggable: false,
    width: 600,
    height: 330,
    position: {
      of: ".modal",
      at: "right+100 top-350",
      my: "right top",
    },
  })
  .prev(".ui-dialog-titlebar")
  .css("background", "#fff")
  .css("margin-top", "10px")
  .css("border", "none");

  $(".modal.protection").on("click", () => {
    $("#dialog").dialog("open");
  });
});
