package com.gmovie.gmovie.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gmovie.gmovie.mapper.UserMapper;

@RestController
@CrossOrigin(origins = "http://localhost:8801") // Cross-Origin Resource Sharing(CORS) 허용
public class AudioController {

    @Autowired
    private UserMapper userMapper;

    @PatchMapping("/saveAudioFilePath")
    // @PostMapping("/saveAudioFilePath") // /saveAudioFilePath 경로로 들어오는 POST 요청을
    // 처리하도록 설정
    // 요청 본문에서 데이터를 추출. 요청에서 받은 JSON 데이터는 Map<String, String> 형태로 파싱
    public ResponseEntity<String> saveAudioFilePath(@RequestBody Map<String, String> payload) {
        String audioFilePath = payload.get("audioFilePath"); // 파일 경로를 audioFilePath 변수에 저장하고
        // userMapper.saveAudioFilePath(audioFilePath)를 호출하여 데이터베이스에 파일 경로를 저장
        userMapper.saveAudioFilePath(audioFilePath);

        return new ResponseEntity<>("File path saved successfully.", HttpStatus.OK);
    }
}
