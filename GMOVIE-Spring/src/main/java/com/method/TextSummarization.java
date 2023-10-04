package com.method;

// import com.google.gson.Gson;
// import com.google.gson.JsonObject;
// import com.google.gson.JsonParser;
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.io.OutputStream;
// import java.net.HttpURLConnection;
// import java.net.URL;
// import java.util.HashMap;
// import java.util.Map;

public class TextSummarization {

    // public static void main(String[] args) throws IOException {
    // String client_id = "0lkdxykojm";
    // String client_secret = "9ZYNw5VGHn4pKbqjL5D2riH36HUfTx4fxJ3RjVxo";

    // String title = "제목을 입력하세요";
    // String content = "요약하려는 긴 텍스트 내용을 입력하세요. 더 긴 내용을 추가해야 정확한 요약 결과를 얻을 수 있습니다.";

    // String summarizedText = summarizeText(client_id, client_secret, title,
    // content);
    // JsonObject jsonObject =
    // JsonParser.parseString(summarizedText).getAsJsonObject();
    // String summary = jsonObject.get("summary").getAsString();

    // System.out.println("요약된 텍스트: \n" + summary);
    // }

    // public static String summarizeText(String client_id, String client_secret,
    // String title, String content) throws IOException {
    // String apiEndpoint =
    // "https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize";

    // URL url = new URL(apiEndpoint);
    // HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    // connection.setRequestMethod("POST");
    // connection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
    // connection.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);
    // connection.setRequestProperty("Content-Type", "application/json");

    // connection.setDoOutput(true);

    // Map<String, Object> requestMap = new HashMap<>();
    // Map<String, Object> documentMap = new HashMap<>();
    // Map<String, Object> optionMap = new HashMap<>();

    // documentMap.put("title", title);
    // documentMap.put("content", content);

    // requestMap.put("document", documentMap);

    // optionMap.put("language", "ko");
    // optionMap.put("model", "general");

    // requestMap.put("option", optionMap);

    // String requestBody = toJsonString(requestMap);

    // try (OutputStream os = connection.getOutputStream()) {
    // byte[] input = requestBody.getBytes("utf-8");
    // os.write(input, 0, input.length);
    // }

    // int responseCode = connection.getResponseCode();

    // if (responseCode == HttpURLConnection.HTTP_OK) {
    // try (BufferedReader in = new BufferedReader(new
    // InputStreamReader(connection.getInputStream(), "UTF-8"))) {
    // StringBuilder response = new StringBuilder();
    // String line;

    // while ((line = in.readLine()) != null) {
    // response.append(line);
    // }

    // return response.toString();
    // }
    // } else {
    // try (BufferedReader errorIn = new BufferedReader(new
    // InputStreamReader(connection.getErrorStream()))) {
    // StringBuilder errorResponse = new StringBuilder();
    // String errorLine;

    // while ((errorLine = errorIn.readLine()) != null) {
    // errorResponse.append(errorLine);
    // }

    // throw new IOException("HTTP 요청 실패. 응답 코드: " + responseCode + "\n오류 응답 본문: " +
    // errorResponse.toString());
    // }
    // }
    // }

    // private static String toJsonString(Map<String, Object> map) {
    // Gson gson = new Gson();
    // return gson.toJson(map);
    // }
}