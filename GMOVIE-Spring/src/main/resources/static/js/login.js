function login() {

    const email = document.getElementById('id').value;
    const password = document.getElementById('password').value;
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    if (!emailPattern.test(email)) {
        alert('올바른 이메일 형식으로 아이디를 입력해 주세요.');
        return;
    }

    if (password === '') {
        alert('비밀번호를 입력해 주세요.');
        return;
    }

    // 创建一个包含用户数据的对象
    const user = { email: email, pwd: password };

    // 发送POST请求
    axios.post("login", user)
        .then((response) => {
            if (response.status === 200) {
                // sessionStorage保存
                const userNo = response.data.no;
                sessionStorage.setItem("userNo", JSON.stringify(userNo));
                // 重定向到其他页面
                window.location.href = "home";
            } else {
                alert(response.data);
            }
        })
        .catch((error) => console.log(error));
}