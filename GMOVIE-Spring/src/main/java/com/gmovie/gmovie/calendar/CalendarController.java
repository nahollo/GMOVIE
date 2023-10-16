package com.gmovie.gmovie.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/calendar") // 定义API端点的根路径
public class CalendarController {

    @Autowired
    private CalendarEventService calendarEventService;

    // 获取所有日历事件
    @GetMapping("/events")
    public ResponseEntity<?> getAllEvents() {
        List<CalendarEventDTO> events = calendarEventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/{userno}")
    public ResponseEntity<List<CalendarEventDTO>> getEventsByUser(@PathVariable int userno) {
        List<CalendarEventDTO> events = calendarEventService.getEventsByUser(userno);
        return ResponseEntity.ok(events);
    }

    // 创建新的日历事件
    @PostMapping("/events")
    public ResponseEntity<?> createEvent(@RequestBody CalendarEventDTO eventDTO) {
        CalendarEventDTO createdEvent = calendarEventService.createEvent(eventDTO);
        return ResponseEntity.ok(createdEvent);
    }

    // 更新日历事件
    @PutMapping("/events/{eventId}")
    public ResponseEntity<?> updateEvent(@PathVariable String eventId, @RequestBody CalendarEventDTO eventDTO) {
        CalendarEventDTO updatedEvent = calendarEventService.updateEvent(eventId, eventDTO);
        return ResponseEntity.ok(updatedEvent);
    }

    // 删除日历事件
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable String eventId) {
        calendarEventService.deleteEvent(eventId);
        return ResponseEntity.ok("Event deleted successfully");
    }

}
