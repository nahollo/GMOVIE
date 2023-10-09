var confirmRrn = false;

function validate() {
    var idRegex = /^[a-zA-Z0-9]{4,12}$/;
    var passwordRegex = /^[a-zA-Z0-9]{4,12}$/;
    var emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    var rrnRegex = /^\d{13}$/;

    var idInput = document.getElementById('my_id').value;
    var passwordInput = document.getElementById('my_password').value;
    var password2Input = document.getElementById('my_password2').value;
    var emailInput = document.getElementById('my_email').value;
    var rrnInput = document.getElementById('my_rrn').value;
    var nameInput = document.getElementById('my_name').value;

    var hobbyInputs = document.getElementsByName('my_hobby');
    var infoInput = document.getElementById('my_info').value;

    if (idInput == "") {
        alert('아이디를 입력해주세요.');
        return false;
    }
    if (!idRegex.test(idInput)) {
        alert('아이디는 4~12자의 영문 대소문자와 숫자로만 입력해야 합니다.');
        document.getElementById('my_id').value = "";
        return false;
    }
    if (passwordInput == "") {
        alert('비밀번호를 입력해주세요.');
        return false;
    }
    if (!passwordRegex.test(passwordInput)) {
        alert('비밀번호는 4~12자의 영문 대소문자와 숫자로만 입력해야 합니다.');
        document.getElementById('my_password').value = "";
        return false;
    }
    if (passwordInput !== password2Input) {
        alert('비밀번호가 일치하지 않습니다.');
        document.getElementById('my_password').value = "";
        document.getElementById('my_password2').value = "";
        return false;
    }
    if (!emailRegex.test(emailInput)) {
        alert('올바른 이메일 주소를 입력해야 합니다.');
        document.getElementById('my_email').value = "";
        return false;
    }
    if (nameInput == "") {
        alert('이름을 입력해주세요.');
        return false;
    }
    if (document.getElementById('zip-code').value == "" && document.getElementById('address-1').value == "" && document.getElementById('address-1-1').value == "" && document.getElementById('address-2').value == "") {
        alert('주소를 입력해주세요.');
        return false;
    }
    if (!confirmRrn) {
        alert('주민등록번호 인증을 해주세요.');
        return false;
    }
    var isHobbySelected = false;
    for (var i = 0; i < hobbyInputs.length; i++) {
        if (hobbyInputs[i].checked) {
            isHobbySelected = true;
            break;
        }
    }
    if (!isHobbySelected) {
        alert('관심분야는 최소 1개 이상 선택해주세요.');
        return false;
    }
    if (infoInput.length < parseInt(20)) {
        alert('자기소개는 20자 이상 작성해주세요');
        return false;
    }
}

function rrnConfirm() {
    var sum = 0;
    var temp = 2;
    var rrnInput = document.getElementById('my_rrn').value;

    if (rrnInput.length !== 13) {
        alert("주민등록번호는 13자리여야 합니다.");
        return false;
    }

    for (var i = 0; i < 12; i++) {
        if (temp == 10) {
            temp = 2;
        }
        sum += parseInt(rrnInput.charAt(i), 10) * temp++;
    }

    var confirm = sum % 11;
    confirm = 11 - confirm;
    if (confirm >= 10) {
        confirm = confirm - 10;
    }
    if (parseInt(rrnInput.charAt(12), 10) === confirm) {
        var year = document.getElementById('my_year');
        var month = document.getElementById('month');
        var day = document.getElementById('day');
        var tempYear = rrnInput.substring(0, 2);
        var tempMonth = rrnInput.substring(2, 4);
        var tempDay = rrnInput.substring(4, 6);
        var sevenNum = rrnInput.charAt(6);

        if (sevenNum > 2) {
            year.value = "20" + tempYear;
        } else {
            year.value = "19" + tempYear;
        }
        month.value = parseInt(tempMonth);
        day.value = parseInt(tempDay);
        confirmRrn = true;

        document.getElementById('my_year').readOnly = true;
        document.getElementById('month').disabled = true;
        document.getElementById('day').disabled = true;
        return true;
    } else {
        alert("주민등록번호가 올바르지 않습니다.");
        return false;
    }

    alert("회원가입이 완료되었습니다!");
    window.location.href = `mailto:${mailAddress.value}?subject=회원가입 완료&body=회원가입을 환영합니다.`;
    return true;
}

function execDaumPostcode() {
    console.log('execDaumPostcode() 함수 실행');
    new daum.Postcode({
        oncomplete: function (data) {
            console.log('제발');
            var addr = '';
            var extraAddr = '';
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }
            if (data.userSelectedType === 'R') {
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                document.getElementById("address-1-1").value = extraAddr;

            } else {
                document.getElementById("address-1-1").value = '';
            }

            document.getElementById('zip-code').value = data.zonecode;
            document.getElementById("address-1").value = addr;
            document.getElementById("address-2").focus();
        }
    }).open();
}