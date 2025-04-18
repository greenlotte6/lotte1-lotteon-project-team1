$(document).ready(function () {
  const $modal = $('#exchange-info-modal');

  // 열기 버튼 클릭 시 외부 HTML 로드
  $('#exchangeModal').on('click', function (e) {
    e.preventDefault();

    // 외부 파일 내용을 모달에 로드 (모달 안에 어떤 div가 있을 거라고 가정)
    $modal.load('/templates/common/myPage/modal/modal7.html', function () {
      $modal.css('display', 'flex');
    });
  });

  // 닫기 버튼 (로드된 HTML 내부에도 닫기 버튼이 있어야 함)
  $(document).on('click', '.close-exchange', function () {
    $modal.css('display', 'none');
  });

  // 모달 바깥 클릭 시 닫기
  $(window).on('click', function (e) {
    if ($(e.target).is($modal)) {
      $modal.css('display', 'none');
    }
  });
});