package com.gmovie.gmovie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

@RestController
@CrossOrigin(origins = "http://localhost:8801")
public class saveAudioFilePath {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/saveAudioFilePath")
    public ResponseEntity<String> postAudioPath(@RequestParam("file") MultipartFile file) {
        // 업로드된 파일을 처리하는 로직
        if (!file.isEmpty()) {
            try {
                // files 폴더는 프로젝트 루트 디렉토리 아래에 위치
                Path filePath = Paths.get("files/" + file.getOriginalFilename());

                if (!Files.exists(filePath.getParent())) {
                    Files.createDirectories(filePath.getParent());
                }

                Files.write(filePath, file.getBytes());

                // DB에 파일 경로 저장하는 로직 (상대 경로 저장)
                jdbcTemplate.update(
                        "INSERT INTO SUMMARY (RECORDING) VALUES (?)",
                        filePath.toString());

                return new ResponseEntity<>("Path saved successfully.", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Failed to upload file: " + e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("File is empty.", HttpStatus.BAD_REQUEST);
        }
    }
}
