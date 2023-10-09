package com.gmovie.gmovie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class SaveAudioFilePath {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/saveAudioFilePath")
    public ResponseEntity<String> postAudioPath(@RequestParam("file") MultipartFile file, HttpSession session) {

        // Get the UUID from the current session.
        String mettingId = (String) session.getAttribute("mettingRoomId");

        if (mettingId == null) {
            return new ResponseEntity<>("로그인 후 이용해주세요.", HttpStatus.UNAUTHORIZED);
        }

        if (!file.isEmpty()) {
            try {
                Path filePath = Paths.get("files/" + file.getOriginalFilename());

                if (!Files.exists(filePath.getParent())) {
                    Files.createDirectories(filePath.getParent());
                }

                Files.write(filePath, file.getBytes());

                // Update the RECORDING column for the row with the current mettingId.
                jdbcTemplate.update(
                        "UPDATE SUMMARY SET RECORDING = ? WHERE METTING_ID = ?",
                        filePath.toString(), mettingId);

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