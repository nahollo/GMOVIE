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

@RestController
@CrossOrigin(origins = "http://localhost:8801") // Cross-Origin Resource Sharing(CORS) 허용
public class saveAudioFilePath {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/saveAudioFilePath")
    public ResponseEntity<String> postAudioPath(@RequestParam("file") MultipartFile file,
            @RequestParam("audioFilePath") String audioFilePath) {
        // 업로드된 파일을 처리하는 로직
        if (!file.isEmpty()) {
            try {
                // DB에 파일 경로 저장하는 로직
                jdbcTemplate.update("INSERT INTO SUMMARY (RECORDING) VALUES (?)",
                        audioFilePath);

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
