var confirmRrn = false;
var userData = null;

function validate() {
    var idRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    var passwordRegex = /^[a-zA-Z0-9]{4,12}$/;

    var idInput = document.getElementById('my_id').value;
    var passwordInput = document.getElementById('my_password').value;
    var password2Input = document.getElementById('my_password2').value;
    // var rrnInput = document.getElementById('my_rrn').value;
    var nameInput = document.getElementById('my_name').value;
    const maleRadioButton = document.getElementById('radio1');
    const femaleRadioButton = document.getElementById('radio2');

    // 获取选定的值
    let selectedGender = null;

    if (maleRadioButton.checked) {
        selectedGender = maleRadioButton.value; // 用户选择了"1"
    } else if (femaleRadioButton.checked) {
        selectedGender = femaleRadioButton.value; // 用户选择了"0"
    }


    if (idInput == "") {
        alert('이메일을 입력해주세요.');
        return false;
    }
    if (!idRegex.test(idInput)) {
        alert('이메일 형식이 정확하지 않습니다.');
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
    if (nameInput == "") {
        alert('이름을 입력해주세요.');
        return false;
    }
    // if(document.getElementById('zip-code').value == "" && document.getElementById('address-1').value == "" &&  document.getElementById('address-2').value == ""){
    //     alert('주소를 입력해주세요.');
    //     return false;
    //  }
    // if (!confirmRrn) {
    //     alert('주민등록번호 인증을 해주세요.');
    //     return false;
    // }

    // if (!confirmRrn) {
    //     alert('주민등록번호 인증을 해주세요.');
    //     return false;
    // }


    const userData = {
        name: nameInput,
        email: idInput,
        pwd: passwordInput,
        gender: selectedGender

    };
    save(userData);

    alert("회원가입이 완료되었습니다!");

    // 회원가입 완료 후 원하는 동작(예: 이메일 전송)을 수행하려면 아래의 코드를 추가합니다.
    // window.location.href = `mailto:${mailAddress.value}?subject=회원가입 완료&body=회원가입을 환영합니다.`;
    return true;
}

// function rrnConfirm() {
//     var sum = 0;
//     var temp = 2;
//     var rrnInput = document.getElementById('my_rrn').value;

//     if (rrnInput.length !== 13) {
//         alert("주민등록번호는 13자리여야 합니다.");
//         return false;
//     }

//     for (var i = 0; i < 12; i++) {
//         if (temp == 10) {
//             temp = 2;
//         }
//         sum += parseInt(rrnInput.charAt(i), 10) * temp++;
//     }

//     var confirm = sum % 11;
//     confirm = 11 - confirm;
//     if (confirm >= 10) {
//         confirm = confirm - 10;
//     }
//     if (parseInt(rrnInput.charAt(12), 10) === confirm) {
//         var year = document.getElementById('my_year');
//         var month = document.getElementById('month');
//         var day = document.getElementById('day');
//         var tempYear = rrnInput.substring(0, 2);
//         var tempMonth = rrnInput.substring(2, 4);
//         var tempDay = rrnInput.substring(4, 6);
//         var sevenNum = rrnInput.charAt(6);

//         if (sevenNum > 2) {
//             year.value = "20" + tempYear;
//         } else {
//             year.value = "19" + tempYear;
//         }
//         month.value = parseInt(tempMonth);
//         day.value = parseInt(tempDay);
//         confirmRrn = true;

//         document.getElementById('my_year').readOnly = true;
//         document.getElementById('month').disabled = true;
//         document.getElementById('day').disabled = true;
//         return true;
//     } else {
//         alert("주민등록번호가 올바르지 않습니다.");
//         return false;
//     }

//     // alert("회원가입이 완료되었습니다!");
//     // window.location.href = `mailto:${mailAddress.value}?subject=회원가입 완료&body=회원가입을 환영합니다.`;
//     // return true;
// }

// function execDaumPostcode() {
//     new daum.Postcode({
//         oncomplete: function (data) {
//             var addr = '';

//             if (data.userSelectedType === 'R') {
//                 addr = data.roadAddress;
//             } else if (data.userSelectedType === 'J') {
//                 addr = data.jibunAddress;
//             }

//             document.getElementById('zip-code').value = data.zonecode;
//             document.getElementById("address-1").value = addr;
//             document.getElementById("address-2").focus();
//         }
//     }).open();
// }


// document.getElementById("login-btn").addEventListener('click', function () {
//     window.location.href = 'lhttp://gmovie.co.kr/login';
// });


function save(userData) {
    if (userData) {
        // userData
        axios.put('save', userData)
            .then(response => {
                console.log(response);
                // alert("회원가입이 완료되었습니다!");
                window.location.href = 'login';
            })
            .catch(error => {
                console.error(error);
            });
    } else {
        console.error("데이터 없습니다.");
    }
}

