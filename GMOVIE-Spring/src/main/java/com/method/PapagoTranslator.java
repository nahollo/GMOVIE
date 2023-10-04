package com.method;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

public class PapagoTranslator {

    public String translateText(String text, String sourceLang, String targetLang) {
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
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
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

    public static void main(String[] args) {
        String textToTranslate = "나는 뭐하면서 사는게 좋을까?";
        String sourceLanguage = "ko"; // 원본 언어 코드
        String targetLanguage = "en"; // 번역할 언어 코드

        String translatedText = translateText(textToTranslate, sourceLanguage, targetLanguage);
        if (translatedText != null) {
            System.out.println("번역 결과: " + translatedText);
        } else {
            System.out.println("번역 실패");
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
