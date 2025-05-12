function updateSequence(sequences) {
  const sequenceNumbers = [];
  for (let sequence of sequences) {
    sequenceNumbers.push(sequence.value);
  }
  const sortedNums = sequenceNumbers.sort((x, y) => {
    return x - y;
  })
  for (let i = 0; i < sortedNums.length; i++) {
    sequences[i].value = sortedNums[i];
  }
}

function onSorted(event, sequenceClassName) {
  const ul = event.target;
  const sequences = ul.querySelectorAll(sequenceClassName);
  updateSequence(sequences);
}

$(() => {
  const ANIME_TYPE = "blind";
  const TRANSITION_TIME = 300;
  const form = $(".post-form");

  form.on("submit", (event) => {
    event.preventDefault();
  })

  //1차 카테고리를 드래그-앤-드롭 방식으로 순서 정렬 가능
  $(".category-area").sortable({
    update: function (event, ui) {
      onSorted(event, ".category-sequence")
    }
  });

  //하위 카테고리를 드래그-앤-드롭 방식으로 순서 정렬 가능
  $(".sub-category").sortable({
    update: function (event, ui) {
      onSorted(event, ".subcategory-sequence")
    }
  });

  //카테고리를 클릭하면 하위 카테고리 영역이 펼쳐짐
  $(".category-title").on("click", (event) => {
    let isOpend = false;
    const options = {};
    if (event.target.tagName.toUpperCase() == "INPUT") {
      return;
    }
    const target = event.target
    .closest(".category")
    .querySelector(".sub-category");

    $(target).toggle(ANIME_TYPE, options, TRANSITION_TIME);
  });

  $(".modify-btn").on("click", (event) => {
    /*
    TODO
    3. 수정 로직 작성
     */
    alert("수정 완료(TODO: 나중에 서버로 데이터 보내기)");
  });

  $(".modify-all-btn").click((event) => {
    const url = form.attr("action");
    const method = form.attr("method")
    $.ajax(url, {
      type: method,
      data: form.serialize(),
      processData: false,
      contentType: "application/x-www-form-urlencoded",
      success: function (response) {
        alert("일괄수정 완료");
      }
    })
  });
});
