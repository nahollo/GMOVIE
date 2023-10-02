package com.method;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class Method {

    public String stt() {
        String openApiURL = "http://aiopen.etri.re.kr:8000/WiseASR/Recognition";
        String accessKey = "60c9c602-3d19-46b6-8590-090f8c87c4f8"; // 발급받은 API Key
        String languageCode = "korean"; // 언어 코드
        String audioFilePath = "C:\\Users\\skgud\\Downloads\\output.wav"; // 녹음된 음성 파일 경로
        String audioContents = null;

        Gson gson = new Gson();

        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        try {
            Path path = Paths.get(audioFilePath);
            byte[] audioBytes = Files.readAllBytes(path);
            audioContents = Base64.getEncoder().encodeToString(audioBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        argument.put("language_code", languageCode);
        argument.put("audio", audioContents);

        request.put("argument", argument);

        URL url;
        Integer responseCode = null;
        String responseBody = null;

        try {
            url = new URL(openApiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", accessKey);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(gson.toJson(request).getBytes(StandardCharsets.UTF_8));
            wr.flush();
            wr.close();

            responseCode = con.getResponseCode();
            InputStream is = con.getInputStream();

            // 인코딩을 지정하여 데이터를 읽음
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[1024];
            int bytesRead;
            while ((bytesRead = reader.read(buffer)) != -1) {
                builder.append(buffer, 0, bytesRead);
            }
            responseBody = builder.toString();

            System.out.println("[responseCode] " + responseCode);
            System.out.println("[responBody]");
            System.out.println("===결과 확인===");

            // JSON 응답 데이터 파싱
            Result result = gson.fromJson(responseBody, Result.class);

            // 파싱된 결과 출력
            System.out.println("인식된 텍스트: " + result.getReturnObject().getRecognized());
            return result.getReturnObject().getRecognized().toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }

    // JSON 응답 구조를 매핑할 클래스 정의
    public static class Result {
        private ReturnObject return_object;

        public ReturnObject getReturnObject() {
            return return_object;
        }

        public static class ReturnObject {
            private String recognized;

            public String getRecognized() {
                return recognized;
            }
        }
    }


    public void base64Encoded() {
        String inputFilePath = "C:\\Users\\skgud\\OneDrive\\Desktop\\test2.m4a"; // 입력 오디오 파일 경로
        String outputFilePath = "C:\\Users\\skgud\\Downloads\\output.wav"; // 변환 후 오디오 파일 경로

        // 1. 음성 파일 변환
        try {
            convertTo16kHz(inputFilePath, outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // 2. Base64로 인코딩
        String base64EncodedAudio = encodeToBase64(outputFilePath);

        // 이제 base64EncodedAudio를 사용하여 API 호출 등의 작업을 수행할 수 있습니다.
        System.out.println("Base64 인코딩된 오디오: " + base64EncodedAudio);
    }

    // 음성 파일을 16kHz로 변환하는 메서드
    private static void convertTo16kHz(String inputFilePath, String outputFilePath) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(
            "ffmpeg",
            "-i", inputFilePath,
            "-ar", "16000", // 16kHz 샘플링 주파수 설정
            outputFilePath
        );

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // WAV 파일을 읽어서 Base64로 인코딩하는 메서드
    private static String encodeToBase64(String filePath) {
        byte[] audioBytes;
        try {
            audioBytes = Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return Base64.getEncoder().encodeToString(audioBytes);
    }


}
