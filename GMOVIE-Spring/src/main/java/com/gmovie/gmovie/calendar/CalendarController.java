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

    // // 获取所有日历事件
    // @GetMapping("/events")
    // public ResponseEntity<?> getAllEvents() {
    // List<CalendarEventDTO> events = calendarEventService.getAllEvents();
    // return ResponseEntity.ok(events);
    // }

    @PostMapping("/addUserToGroup/{userEmail}")
    public ResponseEntity<?> addUserToGroup(@PathVariable String userEmail, @RequestBody MembershipDTO membershipDTO) {
        System.out.println(userEmail);
        // 根据 userEmail 查询用户的 ID，这里假设您有一个 UserService 来执行此操作
        int userno = calendarEventService.findUserIdByEmail(userEmail);
        String groupId = membershipDTO.getId();

    
        if (userno != -1) {
            MembershipDTO newmembershipDTO = new MembershipDTO();
            newmembershipDTO.setId(groupId);
            newmembershipDTO.setUser(userno);
            
            MembershipDTO savedMembership = calendarEventService.savedMembership(newmembershipDTO);
            return ResponseEntity.ok(savedMembership);
        } else {
            return ResponseEntity.badRequest().body("사용자 존재하지 않습니다.");
        }
    }
    

    @PostMapping("/groups")
    public ResponseEntity<?> createGroup(@RequestBody GroupDTO groupDTO) {
        GroupDTO savedGroup = calendarEventService.saveGroup(groupDTO);

        return ResponseEntity.ok(savedGroup);
    }

    @PostMapping("/membership")
    public ResponseEntity<?> createMembership(@RequestBody MembershipDTO membershipDTO) {
        MembershipDTO savedMembership = calendarEventService.savedMembership(membershipDTO);

        return ResponseEntity.ok(savedMembership);
    }

    @GetMapping("/groups/{userno}")
    public ResponseEntity<List<GroupDTO>> getGroupsByUser(@PathVariable int userno) {
        List<String> groupIds = calendarEventService.getGroupsIdsByUser(userno);
        List<GroupDTO> events = calendarEventService.getGroupsByIds(groupIds);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/groupevents/{groupId}")
    public ResponseEntity<List<CalendarEventDTO>> getEventsByUser(@PathVariable String groupId) {
        List<String> eventIds = calendarEventService.getEventIdsByGroup(groupId);
        List<CalendarEventDTO> events = calendarEventService.getEventsByIds(eventIds);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/{userno}")
    public ResponseEntity<List<CalendarEventDTO>> getEventsByUser(@PathVariable int userno) {
        List<String> eventIds = calendarEventService.getEventIdsByUser(userno);
        List<CalendarEventDTO> events = calendarEventService.getEventsByIds(eventIds);
        return ResponseEntity.ok(events);
    }    



    // 创建新的日历事件
    @PostMapping("/events")
    public ResponseEntity<?> createEvent(@RequestBody CalendarEventDTO eventDTO) {
        CalendarEventDTO createdEvent = calendarEventService.createEvent(eventDTO);

        String groupId = eventDTO.getGroupId();
        if (groupId != null) {
        GroupSharingDTO groupSharingDTO = new GroupSharingDTO();
        groupSharingDTO.setEventId(createdEvent.getId()); // 使用新创建事件的ID
        groupSharingDTO.setGroupId(groupId);

        calendarEventService.createGroupSharing(groupSharingDTO);

        } else {

        EventSharingDTO eventSharingDTO = new EventSharingDTO();
        eventSharingDTO.setEventId(createdEvent.getId()); // 使用新创建事件的ID
        eventSharingDTO.setUserId(eventDTO.getUserno());

        calendarEventService.createEventSharing(eventSharingDTO);
        }
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

    @DeleteMapping("/deleteGroup/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable String groupId) {
        List<String> eventIds = calendarEventService.getEventIdsByGroup(groupId);
        for (String eventId : eventIds) {
            calendarEventService.deleteEvent(eventId);
        }
        calendarEventService.deleteGroup(groupId);
        return ResponseEntity.ok("Group deleted successfully");
    }

}
