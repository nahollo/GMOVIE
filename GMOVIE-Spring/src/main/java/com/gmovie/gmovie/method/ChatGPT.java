package com.gmovie.gmovie.method;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPT {

    private static String callChatGPT(String prompt) {
        String apiKey = System.getenv("OPENAI_API_KEY");
        String concept = "회의록을 주제, 회의 일시는 있으면 쓰고 없으면 안쓰고, 참석자, 안건,회의 내용, 결정사항, 향후일정, 특이사항 순서로 회의록을 작성하는 회의록 작성 전문가";

        try {
            URL url = new URL("https://api.openai.com/v1/chat/completions");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + apiKey);
            httpURLConnection.setDoOutput(true);

            String input = "{" +
                "\"model\": \"gpt-4\"," +
                "\"messages\": [" +
                    "{\"role\": \"system\", \"content\": \"" + concept + "\"}," +
                    "{\"role\": \"user\", \"content\": \"" + prompt + "\"}" +
                "]" +
               "}";

            try (OutputStream os = httpURLConnection.getOutputStream()) {
                byte[] inputBytes = input.getBytes("utf-8");
                os.write(inputBytes, 0, inputBytes.length);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
                StringBuilder res = new StringBuilder();
                String resLine;

                while ((resLine = br.readLine()) != null) {
                    res.append(resLine.trim());
                }

                return (res.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public String gptSummary(String text) {
        String prompt = text + " 오타들을 다듬어주고, 다듬어준 회의 내용을 회의록 형식으로 요약해줘.";
        String res = callChatGPT(prompt);
        try {
            JsonObject jsonObject = JsonParser.parseString(res).getAsJsonObject();
            JsonObject messageObject = jsonObject.get("choices").getAsJsonArray().get(0).getAsJsonObject()
                    .get("message").getAsJsonObject();
            String content = messageObject.get("content").getAsString();
            System.out.println(content);

            String contentWithSpaces = content.replace("\t", "　　　　");

            String[] words = contentWithSpaces.split("　");
            StringBuilder formattedContent = new StringBuilder();
            int lineLength = 0;
            int maxLength = 60; // 원하는 줄바꿈 길이를 설정합니다.

            for (String word : words) {
                if (lineLength + word.length() > maxLength) {
                    // 현재 단어를 추가하면 최대 길이를 초과하므로, 줄바꿈을 추가합니다.
                    formattedContent.append("\n");
                    lineLength = 0;
                }
                formattedContent.append(word).append("　");
                lineLength += word.length() + 1; // 공백을 고려해야 합니다.
            }

            String finalContent = formattedContent.toString();
            return finalContent;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "실패";
    }

}