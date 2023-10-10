package com.gmovie.gmovie.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import com.gmovie.gmovie.chat.ChatMessage;

@Mapper
public interface ChatMessageMapper {

    @Insert("INSERT INTO chat_messages (id,sender_no, content, message_type) VALUES (#{id},#{sender_on}, #{content}, #{type})")
    public int insert(ChatMessage message);
}

