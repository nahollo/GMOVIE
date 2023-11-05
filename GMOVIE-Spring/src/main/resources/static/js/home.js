const storedUser = parseInt(sessionStorage.getItem("userNo"), 10); 
const button = document.getElementById("myButton");
button.addEventListener("click", function () {
    window.location.href = "http://gmovie.co.kr:3000?userNo=" + storedUser;
  });

document.addEventListener("DOMContentLoaded", function() {
    var logo = document.getElementById("gmovie-logo");
    var text = document.getElementById("gmovie-text");
    var loginBtn = document.getElementById("login-btn"); // 로그인 버튼을 찾습니다.

    logo.addEventListener("click", function() {
        window.location.href = "home";
    });

    text.addEventListener("click", function() {
        window.location.href = "home";
    });
});

document.getElementById('login-btn').addEventListener('click', function() {
    window.location.href = 'login';
});
