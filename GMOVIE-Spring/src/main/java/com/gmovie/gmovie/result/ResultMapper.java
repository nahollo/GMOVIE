package com.gmovie.gmovie.result;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ResultMapper {
      @Insert("INSERT INTO summary (MEETING_ID, NO, ORIGINAL_STT, SUMMARY, RECORDING) " +
                  "VALUES (#{MEETING_ID}, #{NO}, #{ORIGINAL_STT}, #{SUMMARY}, #{RECORDING})")
      void insertResult(ResultDTO resultDTO);

      @Select("SELECT SUMMARY FROM summary WHERE MEETING_ID = #{meetingId}")
      String selectSTT(String meetingId);
}