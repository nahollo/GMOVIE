package com.gmovie.gmovie.method;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AudioMerge {

    // 병합된 원본 파일의 경로를 저장하기 위한 인스턴스 변수
    private String originalFile;

    // 원본 파일의 경로를 설정하는 메소드
    public void setOriginalFile(String filePath) {
        this.originalFile = filePath; // 인스턴스 변수 originalFile에 filePath 할당
    }

    public void mergeAudioFiles(List<String> audioFiles, String mergedFile) throws IOException {
        File listFile = new File("mylist.txt"); // 임시 리스트 파일 생성

        try (PrintWriter writer = new PrintWriter(new FileWriter(listFile))) {
            for (String audioFile : audioFiles) {
                writer.println("file '" + audioFile.replace("\\", "/") + "'"); // 각 오디오 파일에 대해 'file' 명령어 작성
            }
        }

        List<String> command = new ArrayList<>();

        command.add("ffmpeg"); // ffmpeg 프로그램 호출

        command.add("-f");
        command.add("concat"); // concat demuxer 사용 지정. 여러 개의 미디어 파일을 하나로 합침.

        command.add("-safe");
        command.add("0"); // 외부에서 생성된 리스트 파일이 안전하다는 것을 알려주는 옵션.

        command.add("-i");
        command.add(listFile.getAbsolutePath()); // 입력으로 임시 리스트 파일 사용.

        command.add("-c");
        command.add("copy"); // 코덱 변경 없이 스트림 복사.

        command.add(mergedFile); // 출력할 병합된 오디오파일 경로.

        ProcessBuilder processBuilder = new ProcessBuilder(command);

        try {
            Process process = processBuilder.start(); // 프로세스 시작.
            int exitCode = process.waitFor(); // 프로세스가 종료될 때까지 기다림. 종료 코드 반환.
            System.out.println("ffmpeg process exit code: " + exitCode);

            setOriginalFile(mergedFile); // 원본파일 경로를 병합된 오디오파일의 경로로 설정.
            System.out.println("병합 후 원본 파일: " + originalFile);

        } catch (IOException | InterruptedException e) {
            System.out.println("병합 중 에러 발생: " + e.getMessage());
            e.printStackTrace();
        }

        listFile.delete(); /*
                            * 작업 완료 후 임시 리스트 파일 삭제.
                            * 만약 병합 작업 중 에러가 발생하면 이 파일을 통해 문제를 진단할 수 있으므로
                            * 실제 운영 환경에서는 바로 삭제하지 않고 로깅 등의 처리 권장
                            */
    }

    public void process(String meetingRoomId) {
        List<String> audioFiles = Arrays.asList(
                "C:\\Users\\82105\\Desktop\\MyVoice.m4a",
                "C:\\Users\\82105\\Desktop\\MyVoice2.m4a",
                "C:\\Users\\82105\\Desktop\\MyVoice3.m4a"); // 실제 오디오파일 경로로 수정 필요
        String mergedFileName = "C:\\Users\\82105\\Desktop\\merged.m4a"; // 실제 저장될 위치와 이름으로 수정 필요
        System.out.println("회의룸id: " + meetingRoomId);
        System.out.println("변환된 음성 파일 경로" + mergedFileName);

        try {
            mergeAudioFiles(audioFiles, mergedFileName); // 오디오 파일들을 병합하는 메소드 호출
        } catch (IOException e) {
            System.out.println("병합 중 에러 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getOriginalFile() {
        return this.originalFile;
    }
}