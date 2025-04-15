$(() => {
  //버전 상세 확인 dialog
  let isOpened = false;
  $(".version-detail").dialog({
    width: 800,
    height: 600,
    autoOpen: false,
    draggable: false,
    resizable: false,
    open: () => {
      isOpened = true;
    },
    close: () => {
      isOpened = false;
    },
  });

  $(".version-detail-btn").on("click", () => {
    if (!isOpened) {
      $(".version-detail").dialog("open");
    } else {
      $(".version-detail").dialog("close");
    }
  });

  $(".version-detail .close-btn").on("click", () => {
    $(".version-detail").dialog("close");
  });

  //버전 등록 dialog
  $(".version-registry").dialog({
    width: 800,
    height: 600,
    autoOpen: false,
    draggable: false,
    resizable: false,
  });

  $(".enroll-btn.register-btn").on("click", () => {
    $(".version-registry").dialog("open");
  });
});
