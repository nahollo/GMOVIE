package com.gmovie.gmovie.method;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

import com.google.gson.Gson;

public class Method {
    final String PATH = "C:\\thanks\\GMOVIE_web_RTC\\src\\audio\\";

    public String summary(String roomId) throws IOException {
        Method method = new Method();
        ChatGPT chatGPT = new ChatGPT();

        String originalFile = PATH + roomId + "\\merge.wav";
        String fullFile = PATH + roomId + "\\output.wav";
        String cutFiles = PATH + roomId + "\\audio_segments";

        // 여기에 로딩화면1
        // 1. base64로 인코딩하기
        method.base64Encoded(originalFile, fullFile);

        // 2. 음성 파일을 20초 단위로 자르기
        List<String> audioSegments = method.splitAudioInto20SecondSegments(fullFile, cutFiles);

        // 3. 각 자른 음성 파일에 STT 적용하기
        List<String> sttResults = method.applySTTToAudioSegments(audioSegments);

        // 4. 각 STT 결과를 통합하기
        String combinedResult = method.combineSTTResults(sttResults);
        System.out.println("STT 텍스트: \n" + combinedResult);

        // 여기에 로딩화면2

        // 5. 요약하고 회의록 만들기
        String output = chatGPT.gptSummary(combinedResult);

        return output;

    }

    public void base64Encoded(String originalFile, String fullFile) {
        String inputFilePath = originalFile; // 입력 오디오 파일 경로
        String outputFilePath = fullFile; // 변환 후 오디오 파일 경로

        try {
            convertTo16kHz(inputFilePath, outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Base64 인코딩 완료");
    }

    // 음성 파일을 16kHz로 변환하는 메서드
    private static void convertTo16kHz(String inputFilePath, String outputFilePath) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg",
                "-i", inputFilePath,
                "-ar", "16000", // 16kHz 샘플링 주파수 설정
                outputFilePath);

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 1. 음성 파일을 18초 단위로 자르기
    public List<String> splitAudioInto20SecondSegments(String audioFilePath, String cutFiles) throws IOException {
        List<String> audioSegments = new ArrayList<>();
        String outputDirectory = cutFiles; // 자른 세그먼트를 저장할 디렉토리 경로
        File outputDir = new File(outputDirectory);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg",
                "-i", audioFilePath,
                "-f", "segment",
                "-segment_time", "18",
                "-c", "copy",
                outputDirectory + "\\segment-%03d.wav");

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        File[] segmentFiles = outputDir.listFiles();
        if (segmentFiles != null) {
            for (File segmentFile : segmentFiles) {
                if (segmentFile.isFile()) {
                    audioSegments.add(segmentFile.getAbsolutePath());
                }
            }
        }

        return audioSegments;
    }

    // 2. 각 자른 음성 파일에 STT 적용하기
    public List<String> applySTTToAudioSegments(List<String> audioSegments) {
        List<String> sttResults = new ArrayList<>();
        String openApiURL = "http://aiopen.etri.re.kr:8000/WiseASR/Recognition";
        String accessKey = "60c9c602-3d19-46b6-8590-090f8c87c4f8"; // 발급받은 API Key
        String languageCode = "korean"; // 언어 코드

        /*
         * korean: 한국어 음성인식 코드
         * english: 영어 음성인식 코드
         * japanese: 일본어 음성인식 코드
         * chinese: 중국어 음성인식 코드
         * spanish: 스페인어 음성인식 코드
         * french: 프랑스어 음성인식 코드
         * german: 독일어 음성인식 코드
         * russian: 러시아어 음성인식 코드
         * vietnam: 베트남어 음성인식 코드
         * arabic: 아랍어 음성인식 코드
         * thailand: 태국어 음성인식 코드
         * portuguese: 포르투칼어 음성인식 코드
         */

        Gson gson = new Gson();
        for (String audioSegment : audioSegments) {
            String audioContents = null;
            Map<String, Object> request = new HashMap<>();
            Map<String, String> argument = new HashMap<>();

            try {
                byte[] audioBytes = Files.readAllBytes(Paths.get(audioSegment));
                audioContents = Base64.getEncoder().encodeToString(audioBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

            argument.put("language_code", languageCode);
            argument.put("audio", audioContents);

            request.put("argument", argument);

            URL url;
            Integer responseCode;
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

                // JSON 응답 데이터 파싱
                Result result = gson.fromJson(responseBody, Result.class);

                // 파싱된 결과 출력
                sttResults.add(result.getReturnObject().getRecognized());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sttResults;
    }

    // 3. 각 STT 결과를 통합하기
    public String combineSTTResults(List<String> sttResults) {
        StringBuilder combinedResult = new StringBuilder();
        for (String result : sttResults) {
            combinedResult.append(result).append(" "); // 각 STT 결과를 공백으로 구분하여 합침
        }
        return combinedResult.toString();
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

}