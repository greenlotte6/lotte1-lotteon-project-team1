//유효성 검사에 사용할 정규표현식
const reUid = /^[a-z]+[a-z0-9]{4,19}$/g;
const rePass = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;
const reName = /^[가-힣]{2,10}$/
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

document.addEventListener("DOMContentLoaded", function () {
  // 유효성 검사에 사용할 상태 변수
  let isUidOk = false;
  let isPassOk = false;
  let isNameOk = false;
  let isEmailOk = false;
  let isHpOk = false;

  // 비밀번호 유효성 검사
  const passResult = document.getElementsByClassName("passResult")[0];

  $("input[name='passwordConfirm']").focusout(
      function () {
        const value1 = $("input[name='password']").val();
        const value2 = $("input[name='passwordConfirm']").val();

        if (!value1.match(rePass)) {
          passResult.innerText = "비밀번호는 숫자, 소문자, 대문자 특수문자 조합 8자리";
          passResult.style.color = "red";
          isPassOk = false;
          return;
        }

        if (value1 === value2) {
          passResult.innerText = "사용 가능한 비밀번호 입니다.";
          passResult.style.color = "green";
          isPassOk = true;
        } else {
          passResult.innerText = "비밀번호가 일치하지 않습니다.";
          passResult.style.color = "red";
          isPassOk = false;
        }
      }
  );


  // 휴대폰 번호 유효성 검사 중복확인
  const hpResult = document.getElementsByClassName('hpResult')[0];

  $("input[name='contact']").on('focusout',
      async function () {

        const value = this.value;

        if (!value.match(reHp)) {
          hpResult.innerText = '휴대폰번호가 유효하지 않습니다.(- 포함)';
          hpResult.style.color = 'red';
          isHpOk = false;
          return;
        }

        // 휴대폰 중복체크
        const response = await fetch(`/check/contact/${value}`);
        const data = await response.json();

        if (data.count > 0) {
          hpResult.innerText = '이미 사용중인 휴대폰번호 입니다.';
          hpResult.style.color = 'red';
          isHpOk = false;
        } else {
          hpResult.innerText = '사용 가능한 휴대폰번호 입니다.';
          hpResult.style.color = 'green';
          isHpOk = true;
        }
      });

  // 이메일 유효성 검사
  const btnSendEmail = document.getElementById("btnSendEmail");
  const emailResult = document.querySelector('.emailResult');
  const auth = document.querySelector('.auth');
  //let preventDoubleClick = false;

  btnSendEmail.onclick = async function () {

    const value = $("input[name='email']").val();

    // 이중 클릭 방지
    //if(preventDoubleClick) {
    //   return;
    // }

    if (!value.match(reEmail)) {
      emailResult.innerText = "이메일이 유효하지 않습니다.";
      emailResult.style.color = "red";
      isEmailOk = false;
      return;
    }

    preventDoubleClick = true;
    const response = await fetch(`/check/email/${value}`);
    const data = await response.json();

    if (data.count > 0) {
      emailResult.innerText = "이미 사용중인 이메일 입니다.";
      emailResult.style.color = "red";
      isEmailOk = false;
    } else {
      // 중복되지 않은 이메일이면 인증번호 필드 출력\
      auth.style.display = "block";
    }
  };

  const btnAuthEmail = document.getElementById("btnAuthEmail");

  btnAuthEmail.onclick = async function () {

    const value = $("input[name='auth']").val();

    // JSON 생성
    const jsonData = {
      "code": value
    };

    // 서버 전송
    const response = await fetch("/email/auth", {
      method: "POST", // post로 전송
      headers: {"Content-Type": "application/json"}, // 헤더 요청 정보
      body: JSON.stringify(jsonData) // json데이터 String으로 변환
    });

    const data = await response.json();
    console.log(data);

    if (data) {
      emailResult.innerText = "이메일이 인증 되었습니다.";
      emailResult.style.color = "green";
      isEmailOk = true;
    } else {
      emailResult.innerText = "유효한 인증코드가 아닙니다.";
      emailResult.style.color = "red";
      isEmailOk = false;
    }
  }

  // 최종 폼 전송
  $(".formRegister").submit(
      function (e) {
        console.log("form submit!!!")

        if (!isPassOk) {
          return false;
        }

        if (!isHpOk) {
          return false;
        }

        if (!isEmailOk) {
          return false;
        }

        return true;
      }
  )

});
