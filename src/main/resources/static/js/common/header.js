/**
 * jQuery UI 'menu' 위젯
 */
$(function () {
  $("#menu").menu();
});

/**
 * jQuery UI 'Toggle' 이펙트
 */
$(function () {
  // run the currently selected effect
  function runEffect() {
    var options = {};

    $("#menu").toggle("blind", options, 200);
  }

  $("#drop-btn").on("click", function () {
    runEffect();
  });

  $(".shortcut").on("mouseover", function (event) {
    $(event.target).css("color", "#ef2a23");
  });

  $(".shortcut").on("mouseout", function (event) {
    $(event.target).css("color", "inherit");
  });
});
