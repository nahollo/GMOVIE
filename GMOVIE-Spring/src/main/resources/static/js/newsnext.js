document.addEventListener("DOMContentLoaded", function() {
    var logo = document.getElementById("gmovie-logo");
    var text = document.getElementById("gmovie-text");
    var loginBtn = document.getElementById("login-btn"); // 로그인 버튼을 찾습니다.

    logo.addEventListener("click", function() {
        window.location.href = "home.html";
    });

    text.addEventListener("click", function() {
        window.location.href = "home.html";
    });
});

document.getElementById('login-btn').addEventListener('click', function() {
    window.location.href = 'login.html';
});