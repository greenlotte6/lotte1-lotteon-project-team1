$(function () {
  let isBannerInfoOpened = false;
  $(".banner-info").dialog({
    autoOpen: false,
    draggable: true,
    width: "auto",
    height: "auto",
    close: (event, ui) => {
      isBannerInfoOpened = false;
    },
    open: (event, ui) => {
      isBannerInfoOpened = true;
    },
  });
  $(".banner-info-btn").on("click", function (event) {
    event.preventDefault();
    if (!isBannerInfoOpened) {
      $(".banner-info").dialog("open");
    } else {
      $(".banner-info").dialog("close");
    }
  });
});
