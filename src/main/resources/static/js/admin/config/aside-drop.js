$(function () {
  const ANIMATION_TYPE = "blind";
  const TRANSITION_TIME = 400;

  function doToggle(target) {
    var options = {};
    doToggleWithOptions(options, target);
  }

  function doToggleWithOptions(options, target) {
    $(target).toggle(ANIMATION_TYPE, options, TRANSITION_TIME);
  }

  $("#settings-menu")
  .unbind()
  .on("click", function () {
    doToggle(".settings");
  });

  $("#store-menu")
  .unbind()
  .on("click", function () {
    doToggle(".submenu.store");
  });

  $("#member-menu")
  .unbind()
  .on("click", function () {
    doToggle(".submenu.member");
  });

  $("#product-menu")
  .unbind()
  .on("click", function () {
    doToggle(".submenu.product");
  });

  $("#order-menu")
  .unbind()
  .on("click", function () {
    doToggle(".submenu.order");
  });

  $("#coupon-menu")
  .unbind()
  .on("click", function () {
    doToggle(".submenu.coupon");
  });

  $("#cs-menu")
  .unbind()
  .on("click", function () {
    doToggle(".submenu.customer-service");
  });
});
