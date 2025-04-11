/**
 * jQuery UI 'menu' 위젯
 */
$(function () {
  $("#menu").menu({
    focus: (event, ui) => {
      console.log(event);
      console.log(ui);
    },
  });
});

/**
 * jQuery UI 'Toggle' 이펙트
 */
$(function () {
  // run the currently selected effect
  function runEffect() {
    // get effect type from
    var selectedEffect = "blind";

    // Most effect types need no options passed by default
    var options = {};
    // some effects have required parameters
    if (selectedEffect === "scale") {
      options = { percent: 50 };
    } else if (selectedEffect === "size") {
      options = { to: { width: 200, height: 60 } };
    }

    // Run the effect
    $("#menu").toggle(selectedEffect, options, 500);
  }

  // Set effect from select menu value
  $("#drop-btn").on("click", function () {
    runEffect();
  });
});
