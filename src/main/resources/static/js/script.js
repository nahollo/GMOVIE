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

    // 로그인 버튼을 클릭하면 signup.html로 이동하게 합니다.
    loginBtn.addEventListener("click", function() {
        window.location.href = "signup.html";
    });
});
