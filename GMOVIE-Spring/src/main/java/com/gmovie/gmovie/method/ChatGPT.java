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

        try {
            URL url = new URL("https://api.openai.com/v1/chat/completions");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + apiKey);
            httpURLConnection.setDoOutput(true);

            String input = "{" + "\"model\": \"gpt-4\"," + "\"messages\": ["
                            + "{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"},"
                            + "{\"role\": \"user\", \"content\": \"" + prompt + "\"}"+ "]" + "}";

            try (OutputStream os = httpURLConnection.getOutputStream()) {
                byte[] inputBytes = input.getBytes("utf-8");
                os.write(inputBytes, 0, inputBytes.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
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

    public String gptSummary(String text){
        String prompt = text + "를 다듬고 회의록 버전으로 요약해줘";
        String res = callChatGPT(prompt);
        try {
            JsonObject jsonObject = JsonParser.parseString(res).getAsJsonObject();
            JsonObject messageObject = jsonObject.get("choices").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsJsonObject();
            String content = messageObject.get("content").getAsString();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "실패";
    }
}