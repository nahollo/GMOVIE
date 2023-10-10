package com.gmovie.gmovie.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarEventService {

    @Autowired
    private CalendarEventMapper calendarEventMapper;

    // 获取所有日历事件
    public List<CalendarEventDTO> getAllEvents() {
        // 使用Mapper查询数据库或执行相应的操作
        List<CalendarEventDTO> events = calendarEventMapper.findAll();
        return events;
    }

    // 创建新的日历事件
    public CalendarEventDTO createEvent(CalendarEventDTO eventDTO) {
        // 执行创建事件的业务逻辑，例如保存到数据库
        calendarEventMapper.insert(eventDTO);
        return eventDTO;
    }

    // 更新日历事件
    public CalendarEventDTO updateEvent(String eventId, CalendarEventDTO eventDTO) {
        // 执行更新事件的业务逻辑，例如更新数据库中的事件
        eventDTO.setId(eventId); // 设置事件的ID
        calendarEventMapper.update(eventDTO);
        return eventDTO;
    }

    // 删除日历事件
    public void deleteEvent(String eventId) {
        // 执行删除事件的业务逻辑，例如从数据库中删除事件
        calendarEventMapper.delete(eventId);
    }
}

