$(() => {
  const ANIME_TYPE = "blind";
  const TRANSITION_TIME = 300;

  //1차 카테고리를 드래그-앤-드롭 방식으로 순서 정렬 가능
  $("#category-area").sortable();

  //하위 카테고리를 드래그-앤-드롭 방식으로 순서 정렬 가능
  $(".sub-category").sortable();

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
    /*
    TODO
    2. 일괄수정 로직 작성
     */
    alert("일괄수정 완료");
  });
});

/*
  TODO
  1. 하위 카테고리(sub-category) 정렬 순서 데이터 서버로 보내서 데이터베이스에 저장해야 함.
 */
