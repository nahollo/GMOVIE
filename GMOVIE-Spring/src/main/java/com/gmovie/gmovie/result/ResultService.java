package com.gmovie.gmovie.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService {

      @Autowired
      private ResultMapper resultMapper;

      public void saveResult(ResultDTO resultDTO) {
            resultMapper.insertResult(resultDTO);
      }

      public String getSummary(String meetingId) {
            return resultMapper.selectSTT(meetingId);
      }
}