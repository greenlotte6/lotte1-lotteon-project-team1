function search(versionName) {
  let resJson = null;
  return resJson;
}

function dialogControl(isOpened) {
  if (!isOpened) {
    $(".version-detail").dialog("open");
  } else {
    $(".version-detail").dialog("close");
  }
}

$(() => {
  //버전 상세 확인 dialog
  const dialogVersionName = $("#version-detail-name")
  const dialogVersionDesc = $("#version-detail-description")
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

  $(".version-detail-btn").on("click", async (event) => {
    dialogControl(isOpened)
    const versionName = event.target.name;
    $.ajax({
      url: `/api/admin/config/version/search?version=${versionName}`,
      dataType: "json",
      success: function (res) {
        dialogVersionName.text(res["version"]);
        dialogVersionDesc.text(res["description"])
        console.log(dialogVersionDesc)
      },
      error: function (err) {
        alert("에러 발생")
      }
    })
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
