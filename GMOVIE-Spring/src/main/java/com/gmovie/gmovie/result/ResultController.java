package com.gmovie.gmovie.result;

import java.util.Map;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultController {

      @Autowired
      private ResultService resultService;

      @PostMapping("/saveResult")
      public String saveResult(@RequestBody Map<String, Object> requestData) {

            int userId = (int) requestData.get("userId");
            String meetingRoomId = (String) requestData.get("meetingRoomId");
            String originalFile = (String) requestData.get("originalFile");
            String summarizedText = (String) requestData.get("summarizedText");
            String combinedResult = (String) requestData.get("combinedResult");

            // 파일 이름만 추출
            Path originalFilePath = Paths.get(originalFile);
            String fileName = originalFilePath.getFileName().toString();

            // 새로운 저장 경로 생성 "files/MEETING_ID/fileName"
            Path newFilePath = Paths.get("files", meetingRoomId, fileName);

            try {
                  if (!Files.exists(newFilePath.getParent())) {
                        Files.createDirectories(newFilePath.getParent());
                  }

                  // 원본 파일을 새 위치로 복사
                  // Files.copy(originalFilePath, newFilePath);
            } catch (Exception e) {
                  e.printStackTrace();
                  return "Error while saving the file.";
            }

            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setNO(userId);
            resultDTO.setMEETING_ID(meetingRoomId);
            // resultDTO.setRECORDING(originalFile);
            resultDTO.setRECORDING(newFilePath.toString()); // 새로운 경로를 DB에 저장
            resultDTO.setSUMMARY(summarizedText);
            resultDTO.setORIGINAL_STT(combinedResult);

            resultService.saveResult(resultDTO);

            return "Result saved to the database.";
      }
}