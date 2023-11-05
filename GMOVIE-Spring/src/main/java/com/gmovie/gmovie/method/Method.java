package com.gmovie.gmovie.method;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Method {

    public Map<String, String> summary(String meetingRoomId) throws IOException {

        String userHome = System.getProperty("user.home");
        String osName = System.getProperty("os.name").toLowerCase();
        String desktopPath;

        if (osName.contains("win")) {
            // Windows
            desktopPath = userHome + File.separator + "Desktop" + File.separator;
        } else if (osName.contains("mac")) {
            // Mac
            desktopPath = userHome + File.separator + "Desktop" + File.separator;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.indexOf("aix") > 0) {
            // Unix or Linux
            desktopPath = userHome + File.separator + "Desktop" + File.separator;
        } else {
            // Fallback to user home directory
            desktopPath = userHome + File.separator;
        }

        AudioMerge audioMerge = new AudioMerge();
        audioMerge.process(meetingRoomId);

        String originalFile = audioMerge.getOriginalFile();
        String originalFile2 = meetingRoomId + File.separator + "MyVoice.m4a";
        String fullFile = desktopPath + meetingRoomId + File.separator + "output.wav";
        String cutFiles = desktopPath + "audio_segments";

        System.out.println("Method클래스 originalFile 경로: " + originalFile);

        // Create directories as needed
        try {
            Files.createDirectories(Paths.get(desktopPath, meetingRoomId));
        } catch (IOException e) {
            System.err.println("An error occurred while creating directories: " + e.getMessage());
        }

        // 1. base64로 인코딩하기
        base64Encoded(originalFile, fullFile);

        // 2. 음성 파일을 20초 단위로 자르기
        List<String> audioSegments = splitAudioInto20SecondSegments(fullFile, cutFiles);

        // 3. 각 자른 음성 파일에 STT 적용하기
        List<String> sttResults = applySTTToAudioSegments(audioSegments);

        // 4. 각 STT 결과를 통합하기
        String combinedResult = combineSTTResults(sttResults);
        System.out.println("STT 텍스트: \n" + combinedResult);

        // 5. STT 결과 번역
        String tempText = translateText(combinedResult, "ko", "en");
        String tempText2 = translateText(tempText, "en", "ko");

        System.out.println("한국어에서 영어로 번역된 텍스트: \n" + tempText);
        System.out.println("영어에서 한국어로 번역된 텍스트: \n" + tempText2);

        // 6. 텍스트 요약
        String summarizedText = summarizeText(combinedResult);
        System.out.println("요약된 텍스트: \n" + summarizedText);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("originalFile", originalFile2);
        resultMap.put("summarizedText", summarizedText);
        resultMap.put("combinedResult", combinedResult);

        return resultMap;
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

        String base64EncodedAudio = encodeToBase64(outputFilePath);

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

    // 4. 텍스트 요약
    public String summarizeText(String text) throws IOException {
        String client_id = "0lkdxykojm";
        String client_secret = "9ZYNw5VGHn4pKbqjL5D2riH36HUfTx4fxJ3RjVxo";
        String title = "요약된 텍스트";
        String content = text;

        String summarizedText = summarizeText(client_id, client_secret, title, content);
        JsonObject jsonObject = JsonParser.parseString(summarizedText).getAsJsonObject();
        String summary = jsonObject.get("summary").getAsString();

        return summary;
    }

    public String summarizeText(String client_id, String client_secret, String title, String content)
            throws IOException {
        String apiEndpoint = "https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize";

        URL url = new URL(apiEndpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
        connection.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);
        connection.setRequestProperty("Content-Type", "application/json");

        connection.setDoOutput(true);

        Map<String, Object> requestMap = new HashMap<>();
        Map<String, Object> documentMap = new HashMap<>();
        Map<String, Object> optionMap = new HashMap<>();

        documentMap.put("title", title);
        documentMap.put("content", content);

        requestMap.put("document", documentMap);

        optionMap.put("language", "ko");
        optionMap.put("model", "general");

        requestMap.put("option", optionMap);

        String requestBody = toJsonString(requestMap);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                return response.toString();
            }
        } else {
            try (BufferedReader errorIn = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                StringBuilder errorResponse = new StringBuilder();
                String errorLine;

                while ((errorLine = errorIn.readLine()) != null) {
                    errorResponse.append(errorLine);
                }

                throw new IOException(
                        "HTTP 요청 실패. 응답 코드: " + responseCode + "\n오류 응답 본문: " + errorResponse.toString());
            }
        }
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

    private String toJsonString(Map<String, Object> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public static String translateText(String text, String sourceLang, String targetLang) {
        String API_URL = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";
        String CLIENT_ID = "dao6nl2gmn";
        String CLIENT_SECRET = "03B6UlrPGYr5HoGpPw7aSVZaoMBkCnKat3fRCnli";
        try {
            // API 요청 헤더 설정
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
            connection.setRequestProperty("X-NCP-APIGW-API-KEY", CLIENT_SECRET);

            // API 요청 본문 설정
            String postData = "source=" + sourceLang + "&target=" + targetLang + "&text=" + text;
            byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);
            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.write(postDataBytes);
            outputStream.flush();
            outputStream.close();

            // API 응답 읽기
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                Gson gson = new Gson();
                TranslationResponse translationResponse = gson.fromJson(response.toString(), TranslationResponse.class);
                String translatedText = translationResponse.getMessage().getResult().getTranslatedText();
                return translatedText;
            } else {
                System.err.println("HTTP Error: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

class TranslationResponse {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public static class Message {
        private Result result;

        public Result getResult() {
            return result;
        }
    }

    public static class Result {
        private String translatedText;

        public String getTranslatedText() {
            return translatedText;
        }
    }
}
