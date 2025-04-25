
    function setAction(url) {
        // 폼의 action을 클릭한 버튼에 맞는 URL로 변경
         document.querySelector('.detail').action = url;

        const form = document.querySelector('.detail');
       // form.action = url;

        //form.method = 'POST';

        // 폼을 제출합니다.
        form.submit();


        // get방식 파라미터 넘길 때
        const params = new URLSearchParams(new FormData(form)).toString(); // 폼 데이터를 쿼리 문자열로 변환
        form.action = `${url}?${params}`; // URL에 쿼리 파라미터 추가


    }
