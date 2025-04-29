import {initDialog} from "../../common/admin/modal.mjs";

$(() => {
  function render(res) {
    console.log(res["gender"])
    const userJson = res["memberId"]["user"];
    $(".registry-modal input[name='memberId.user.id']").val(userJson["id"])
    $(".registry-modal input[name='name']").val(res["name"])
    if (res["gender"] === "m") {
      $("#male").prop("checked", true);
    } else if (res["gender"] === "f") {
      $("#female").prop("checked", true);
    }
    $(".registry-modal input[name='memberId.user.email']").val(
        userJson["email"])
    $(".registry-modal input[name='memberId.user.contact']").val(
        userJson["contact"])
    $(".registry-modal input[name='memberId.user.zip']").val(userJson["zip"]);
    $(".registry-modal input[name='memberId.user.address']").val(
        userJson["address"])
    $(".registry-modal input[name='memberId.user.addressDetail']").val(
        userJson["addressDetail"]);
    $(".registry-modal .memberId.user.registerDate").text(
        userJson["registerDate"]);
    $(".registry-modal .recentLoginDate").text(res["recentLoginDate"]);
    $("#member-etc").val(res["description"]);

    $(".registry-modal").off("dialogopen.getInfo")//.dialogopen.getInfo 이벤트 핸들러 제거
  }

  function getMemberInfo(memberId) {
    console.log("Request information about " + memberId)
    $.ajax(`/api/admin/member/search?id=${memberId}`, {
      type: "GET",
      dataType: "json",
      contentType: "application/json;utf-8",
      success: (res) => {
        render(res);
      },
      error: (err) => {
        alert("상세 정보 불러오기 중 에러가 발생했습니다.")
      }
    });
  }

  const openBtn = $(".registry-modal-btn")
  initDialog(".registry-modal")

  openBtn.click((event) => {
    const userId = event.target.parentNode.parentNode.querySelector(
        ".member-id").innerText;
    console.log(userId)
    $(".registry-modal").on("dialogopen.getInfo", (event, ui) => {
      getMemberInfo(userId);
    }) //dialog가 열릴 때 이벤트 핸들러 등록. dialogopen.getInfo에서 getInfo는 namespace
    $(".registry-modal").dialog("open")
  })
})