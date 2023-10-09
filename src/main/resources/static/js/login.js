document.addEventListener("DOMContentLoaded", function() {
    var logo = document.getElementById("gmovie-logo");
    var text = document.getElementById("gmovie-text");
    var loginBtn = document.getElementById("login-btn");

    logo.addEventListener("click", function() {
        window.location.href = "home.html";
    });

    text.addEventListener("click", function() {
        window.location.href = "home.html";
    });

    loginBtn.addEventListener('click', function() {
        window.location.href = 'login.html';
    });

    const form = document.querySelector('.custom-form');
    form.addEventListener('submit', function(event) {
        // 아이디 (이메일 형식) 유효성 검사
        const email = document.getElementById('exampleInputEmail1').value;
        const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        if (!emailPattern.test(email)) {
            alert('올바른 이메일 형식으로 아이디를 입력해 주세요.');
            event.preventDefault();
            return;
        }

        // 비밀번호 유효성 검사
        const password = document.getElementById('exampleInputPassword1').value;
        if (password === '') {
            alert('비밀번호를 입력해 주세요.');
            event.preventDefault();
            return;
        }

        // 비밀번호 확인 유효성 검사
        const confirmPassword = document.getElementById('exampleInputPassword2').value;
        if (confirmPassword !== password) {
            alert('비밀번호가 일치하지 않습니다.');
            event.preventDefault();
            return;
        }

        // 체크박스 유효성 검사
        const isChecked = document.getElementById('exampleCheck1').checked;
        if (!isChecked) {
            alert('본인 확인 체크박스를 체크해 주세요.');
            event.preventDefault();
            return;
        }
    });
});

document.getElementById("signup").addEventListener("click", function(event) {
    event.preventDefault();
    window.location.href = "signup.html";
});
