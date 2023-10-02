package com.gmovie.gmovie.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List; // List를 import해야 합니다.

import org.apache.http.HttpHeaders;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus; // HttpStatus를 import해야 합니다.
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TranslationService {

    @Value("iuDfWe7JeUG9abzQib9G")
    private String clientId;

    @Value("JiVminNxZx")
    private String clientSecret;

    private final String apiUrl = "https://openapi.naver.com/v1/papago/n2mt";

    public String engToKor(String text) {
        return translate(text, "en", "ko");
    }

    public String korToEng(String text) {
        return translate(text, "ko", "en");
    }

    private String translate(String text, String sourceLang, String targetLang) {
    try {
        // HTTP 클라이언트 생성
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // HTTP POST 요청 생성
        HttpPost httpPost = new HttpPost(apiUrl);
        
        // 요청 헤더 설정
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setHeader("X-Naver-Client-Id", clientId);
        httpPost.setHeader("X-Naver-Client-Secret", clientSecret);

        // 요청 본문 생성
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("source", sourceLang));
        params.add(new BasicNameValuePair("target", targetLang));
        params.add(new BasicNameValuePair("text", text));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        // HTTP 요청 실행
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // 응답 코드 확인
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            // 응답 본문을 문자열로 변환
            String responseBody = EntityUtils.toString(response.getEntity());
            
            // JSON 파싱을 통해 번역 결과 추출
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            String translatedText = rootNode.path("message").path("result").path("translatedText").asText();
            
            // 번역 결과 반환
            return translatedText;
        } else {
            // HTTP 요청 실패 처리
            return "번역 실패";
        }
    } catch (IOException e) {
        // 예외 처리
        e.printStackTrace();
        return "번역 실패";
    }
}
}