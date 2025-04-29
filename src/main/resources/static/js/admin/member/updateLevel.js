/*
TODO: 수정버튼 클릭 시 해당 유저의 id를 이용해 서버에 get 요청하기
*/
$(() => {
  const updateBtn = $(".crud-btn-area > .enroll-btn.delete-btn")

  updateBtn.click((event) => {
    const checkboxes = $(".item-checkbox")
    const checkedBoxes = [];
    for (let checkBox of checkboxes) {
      if (checkBox.checked) {
        console.log("Checked box found")
        checkedBoxes.push(checkBox);
      }
    }

    if (checkedBoxes.length === 0) {
      alert("회원을 선택해주세요")
    } else {
      const json = [];
      for (let box of checkedBoxes) {
        if (box.checked) {
          // 선택된 level <select> 가져오기
          const memberId = box.parentNode.parentNode
          .querySelector(".member-id")
          .getAttribute("data-value")
          const level = box.parentNode
          .parentNode
          .querySelector("select[name='level']")

          const memberIdJson = {};
          const userJson = {}
          const memberJson = {};
          const newLevel = level.options[level.selectedIndex].value;
          userJson["id"] = memberId;
          memberIdJson["user"] = userJson;
          memberJson["memberId"] = memberIdJson;
          memberJson["level"] = newLevel;
          json.push(memberJson)

        }
      }

      console.log(JSON.stringify(json));

      $.ajax("/api/admin/member/update", {
        type: "PUT",
        dataType: "json",
        contentType: "application/json;utf-8",
        data: JSON.stringify(json),
        success: function (res) {
          alert("업데이트 완료")
          location.reload();
        },
        error: function (err) {
          alert("에러 발생")
        }
      })
    }

  })
})
