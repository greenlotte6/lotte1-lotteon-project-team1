/*
  작성자: 이현민(id3ntity99)
  내용: jQuery UI Dialog에 사용되는 옵션 객체와 Dialog 초기화 함수를 선언 및 정의하고 있는 자바스크립트 모듈
 */

export const defaultOptions = {
  autoOpen: false,
  draggable: false,
  resizable: false,
  width: 860,
  height: "auto",
};

export function initDialog(target) {
  initDialogWithOptions(target, defaultOptions);
}

export function initDialogWithOptions(target, options) {
  console.log(
    `Initializing ${target} as a dialog with options ${defaultOptions.autoOpen}`
  );
  $(target).dialog(options);
}
