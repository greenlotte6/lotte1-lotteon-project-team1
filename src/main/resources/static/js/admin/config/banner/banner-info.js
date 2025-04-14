$(function () {
  let isOpened = false;
  $(".banner-info").dialog({
    autoOpen: false,
    draggable: false,
    close: (event, ui) => {
      isOpened = false;
    },
    open: (event, ui) => {
      isOpened = true;
    },
  });
  $(".banner-info-btn").on("click", function () {
    if (!isOpened) {
      $(".banner-info").dialog("open");
    } else {
      $(".banner-info").dialog("close");
    }
  });
});
