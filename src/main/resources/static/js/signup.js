    // 유효성 검사 메서드
    function Validation() {
        //변수에 저장
        var uid = document.getElementById("uid")
        var pw = document.getElementById("pw")
        var cpw = document.getElementById("cpw")
        var mail = document.getElementById("mail")
        var name = document.getElementById("uname")
        var year = document.getElementById("year")
        var month = document.getElementById("month")
        var day = document.getElementById("day")
        var hobby = document.getElementsByName("hobby")
        var me = document.getElementById("me")

        // 정규식
        // id, pw
        var regIdPw = /^[a-zA-Z0-9]{4,12}$/;
        // 이름
        var regName = /^[가-힣a-zA-Z]{2,15}$/;
        // 이메일
        var regMail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
        // 년도
        var regYear = /^[1-2]{1}[0-9]{0,4}$/;

        //아이디 확인
        if(uid.value == ""){
            alert("아이디를 입력하세요.")
            uid.focus();
            return false;
        }
        //아이디 영어 대소문자 확인
        else if(!regIdPw.test(uid.value)){
            alert("4~12자 영문 대소문자, 숫자만 입력하세요.")
            uid.focus();
            return false;
        }

        //비밀번호 확인
        if(pw.value == ""){
            alert("비밀번호를 입력하세요.")
            pw.focus();
            return false;
        }
        //비밀번호 영어 대소문자 확인
        else if(!regIdPw.test(pw.value)){
            alert("4~12자 영문 대소문자, 숫자만 입력하세요.")
            pw.focus();
            return false;
        }
        //비밀번호와 아이디 비교
        else if(pw.value == uid.value){
            alert("아이디와 동일한 비밀번호를 사용할 수 없습니다.")
            pw.focus();
            return false;
        }


        //비밀번호 확인
        if(cpw.value !== pw.value){
            alert("비밀번호와 동일하지 않습니다.")
            cpw.focus();
            return false;
        }

        //메일주소 확인
        if(mail.value.length == 0){
            alert("메일주소를 입력하세요.")
            mail.focus();
            return false;
        }

        else if(!regMail.test(mail.value)){
            alert("잘못된 이메일 형식입니다.")
            mail.focus();
            return false;
        }

        //이름 확인 = 한글과 영어만 가능하도록
        if(uname.value == ""){
            alert("이름을 입력하세요.")
            uname.focus();
            return false;
        }

        else if(!regName.test(uname.value)){
            alert("최소 2글자 이상, 한글과 영어만 입력하세요.")
            uname.focus();
            return false;
        }

        //생일 확인
        if(year.value == ""){
            alert("년도를 입력하세요.")
            year.focus();
            return false;
        }

        else if(!regYear.test(year.value)){
            alert("년도를 정확하게 입력해주세요.")
            year.focus();
            return false;
        }

        else if(!(year.value >=1900 && year.value <= 2050)){
            alert("년도를 정확하게 입력해주세요.")
            year.focus();
            return false;
        }

        //관심분야 확인
        if(!checkedHobby(hobby)){
            alert("관심분야를 체크하세요.")
            hobby.focus();
            return false;
        }

        //자기소개 확인
        if(me.value.length <= 10){
            alert("자기소개는 최소 10글자를 입력해주세요.")
            me.focus();
            return false;
        }

        // 유효성 문제 없을 시 폼에 submit
        document.joinForm.submit();
    }

    //관심분야 체크 확인
    function checkedHobby(arr){
        for(var i=0; i<arr.length; i++){
            if(arr[i].checked == true){
                return true;
            }
        }
        return false;
    }