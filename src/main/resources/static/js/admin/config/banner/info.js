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

  let isRegistryOpened;
  $(".banner-registry").dialog({
    width: 735,
    height: 590,
    autoOpen: false,
    draggable: false,
  });

  $(".register-btn").on("click", () => {
    $(".banner-registry").dialog("open");
  });
});
