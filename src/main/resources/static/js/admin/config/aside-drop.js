$(function () {
  const ANIMATION_TYPE = "blind";
  const TRANSITION_TIME = 300;
  function doToggle(target) {
    let options = {};
    doToggleWithOptions(options, target);
  }

  function doToggleWithOptions(options, target) {
    $(target).toggle(ANIMATION_TYPE, options, TRANSITION_TIME);
  }

  $("#settings-menu").on("click", function () {
    doToggle(".submenu.settings");
  });

  $("#store-menu").on("click", function () {
    doToggle(".submenu.store");
  });

  $("#member-menu").on("click", function () {
    doToggle(".submenu.member");
  });

  $("#product-menu").on("click", function () {
    doToggle(".submenu.product");
  });

  $("#order-menu").on("click", function () {
    doToggle(".submenu.order");
  });

  $("#coupon-menu").on("click", function () {
    doToggle(".submenu.coupon");
  });

  $("#cs-menu").on("click", function () {
    doToggle(".submenu.customer-service");
  });
});
