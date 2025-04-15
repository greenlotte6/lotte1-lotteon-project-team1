$(function () {
  let isBannerInfoOpened = false;
  $(".banner-info").dialog({
    autoOpen: false,
    draggable: false,
    close: (event, ui) => {
      isBannerInfoOpened = false;
    },
    open: (event, ui) => {
      isBannerInfoOpened = true;
    },
  });
  $(".banner-info-btn").on("click", function () {
    if (!isBannerInfoOpened) {
      $(".banner-info").dialog("open");
    } else {
      $(".banner-info").dialog("close");
    }
  });
});
