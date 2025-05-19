$(function () {
  // jQuery UI menu 초기화 + 기본적으로 숨김
  $("#menu").menu().hide();

  let isMenuShown = false;

  $("#drop-btn").on("click", function () {
    if (!isMenuShown) {
      $("#menu").show("blind", {}, 200);
    } else {
      $("#menu").hide("blind", {}, 200);
    }
    isMenuShown = !isMenuShown;
  });

  // shortcut hover 효과
  $(".shortcut").on("mouseover", function (event) {
    $(event.target).css("color", "#ef2a23");
  });

  $(".shortcut").on("mouseout", function (event) {
    $(event.target).css("color", "inherit");
  });
});