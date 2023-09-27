package com.method;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class AudioConversionAndBase64Encoding {

    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\skgud\\OneDrive\\Desktop\\test.m4a"; // 입력 오디오 파일 경로
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

