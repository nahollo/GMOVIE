document.addEventListener("DOMContentLoaded", function() {
    var logo = document.getElementById("gmovie-logo");
    var text = document.getElementById("gmovie-text");
    var loginBtn = document.getElementById("login-btn");

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

function showSummary() {
    // // 요약본 코드를 가져옵니다. (예: API 호출 등을 통해 실제 요약본을 가져올 수 있습니다)
    var code = document.getElementById('summaryCode').value;

    // // TODO: 입력된 코드를 바탕으로 실제 요약본 데이터를 가져오는 작업 수행

    // // 입력 창과 확인 버튼을 숨깁니다.
    // document.querySelector('.input-container').style.display = 'none';

    // // 요약본 컨테이너를 표시합니다.
    // document.getElementById('summaryContent').style.display = 'block';

    let meetingRoomId = code; // 실제 회의실 ID로 변경

        fetch("/executeJavaCode", {
          method: "POST",
          body: JSON.stringify({
            id: meetingRoomId,
          }),
          headers: {
            "Content-Type": "application/json",
          },
        })
          .then((response) => response.json())
          .then((apiData) => {
            // 서버에서 받아온 데이터(apiData)를 화면에 출력
            // data.originalFile = apiData.originalFile;
            // data.summarizedText = apiData.summarizedText;
            // data.combinedResult = apiData.combinedResult;
            alert(data.originalFile);
          })
          .catch((error) => {
            console.error("Java 코드 실행 중 오류가 발생했습니다.: ", error);
          });
    
}