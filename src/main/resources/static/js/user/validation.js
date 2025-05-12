// 정규표현식
const reUid = /^[a-z]+[a-z0-9]{4,19}$/g;
const rePass = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
const reName = /^[가-힣]{2,10}$/;
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

document.addEventListener("DOMContentLoaded", function () {
    let isUidOk = false;
    let isPassOk = false;
    let isNameOk = false;
    let isEmailOk = false;
    let isHpOk = false;

    function getPrefix() {
        const uidInput = document.querySelector("input[name$='.user.id']");
        if (!uidInput) return "";
        if (uidInput.name.includes("memberId")) return "memberId.user.";
        if (uidInput.name.includes("sellerId")) return "sellerId.user.";
        return "";
    }

    const prefix = getPrefix();

    // 아이디 중복확인
    const btnCheckId = document.getElementById("btnCheckId") || document.getElementById("btnCheckUid");
    const IdResult = document.querySelector(".IdResult") || document.querySelector(".uidResult");

    btnCheckId.onclick = function () {
        const value = $(`input[name='${prefix}id']`).val();
        if (!value.match(reUid)) {
            IdResult.innerText = "아이디 형식에 맞지 않습니다.";
            IdResult.style.color = "red";
            isUidOk = false;
            return;
        }

        fetch(`/check/id/${value}`)
            .then(res => res.json())
            .then(data => {
                if (data.count > 0) {
                    IdResult.innerText = "이미 사용중인 아이디입니다.";
                    IdResult.style.color = "red";
                    isUidOk = false;
                } else {
                    IdResult.innerText = "사용 가능한 아이디입니다.";
                    IdResult.style.color = "green";
                    isUidOk = true;
                }
            });
    };

    // 비밀번호 확인
    const passResult = document.querySelector(".passResult");
    $("input[name='passwordConfirm']").on("focusout", function () {
        const pw1 = $(`input[name='${prefix}password']`).val();
        const pw2 = $("input[name='passwordConfirm']").val();

        if (!pw1.match(rePass)) {
            passResult.innerText = "비밀번호는 영문/숫자/특수문자 포함 8~16자";
            passResult.style.color = "red";
            isPassOk = false;
        } else if (pw1 !== pw2) {
            passResult.innerText = "비밀번호가 일치하지 않습니다.";
            passResult.style.color = "red";
            isPassOk = false;
        } else {
            passResult.innerText = "사용 가능한 비밀번호입니다.";
            passResult.style.color = "green";
            isPassOk = true;
        }
    });

    // 이름 확인
    const nameResult = document.querySelector(".nameResult");
    $("input[name='name'], input[name='ceo']").on("focusout", function () {
        const value = this.value;
        if (!value.match(reName)) {
            nameResult.innerText = "이름이 유효하지 않습니다.";
            nameResult.style.color = "red";
            isNameOk = false;
        } else {
            nameResult.innerText = "";
            isNameOk = true;
        }
    });

    // 휴대폰 중복확인
    const hpResult = document.querySelector(".hpResult");
    $(`input[name='${prefix}contact']`).on("focusout", async function () {
        const value = this.value;
        if (!value.match(reHp)) {
            hpResult.innerText = "휴대폰번호가 유효하지 않습니다.";
            hpResult.style.color = "red";
            isHpOk = false;
            return;
        }

        const res = await fetch(`/check/contact/${value}`);
        const data = await res.json();

        if (data.count > 0) {
            hpResult.innerText = "이미 사용중인 번호입니다.";
            hpResult.style.color = "red";
            isHpOk = false;
        } else {
            hpResult.innerText = "사용 가능한 번호입니다.";
            hpResult.style.color = "green";
            isHpOk = true;
        }
    });

    // 이메일 중복 + 인증코드 전송
    const btnSendEmail = document.getElementById("btnSendEmail");
    const emailResult = document.querySelector(".emailResult");
    const auth = document.querySelector(".auth");
    let preventDoubleClick = false;

    btnSendEmail.onclick = async function () {
        const value = $(`input[name='${prefix}email']`).val();
        if (!value.match(reEmail)) {
            emailResult.innerText = "이메일이 유효하지 않습니다.";
            emailResult.style.color = "red";
            isEmailOk = false;
            return;
        }

        if (preventDoubleClick) return;
        preventDoubleClick = true;

        const res = await fetch(`/check/email/${value}`);
        const data = await res.json();

        if (data.count > 0) {
            emailResult.innerText = "이미 사용중인 이메일입니다.";
            emailResult.style.color = "red";
            isEmailOk = false;
        } else {
            emailResult.innerText = "인증코드를 발송했습니다.";
            emailResult.style.color = "green";
            auth.style.display = "block";
        }
    };

    // 인증코드 검증
    const btnAuthEmail = document.getElementById("btnAuthEmail");
    btnAuthEmail.onclick = async function () {
        const code = $("input[name='auth']").val();
        const res = await fetch("/email/auth", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ code })
        });

        const data = await res.json();
        if (data) {
            emailResult.innerText = "이메일 인증 완료되었습니다.";
            emailResult.style.color = "green";
            isEmailOk = true;
        } else {
            emailResult.innerText = "인증코드가 일치하지 않습니다.";
            emailResult.style.color = "red";
            isEmailOk = false;
        }
    };

    // 제출 전 최종 검증
    document.addEventListener("DOMContentLoaded", function () {
        const form = document.querySelector(".formRegister");

        form.addEventListener("submit", function (e) {
            console.log({
                isUidOk, isPassOk, isNameOk, isHpOk, isEmailOk
            });

            if (!isUidOk || !isPassOk || !isNameOk || !isHpOk || !isEmailOk) {
                e.preventDefault();
                alert("모든 입력값을 정확히 확인해주세요.");
                return false;
            }
        });
    });
});
