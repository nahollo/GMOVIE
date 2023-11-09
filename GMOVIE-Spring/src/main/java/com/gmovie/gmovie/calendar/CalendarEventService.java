package com.gmovie.gmovie.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import java.util.List;

@Service
public class CalendarEventService {

    @Autowired
    private CalendarEventMapper calendarEventMapper;
    // // 获取所有日历事件
    // public List<CalendarEventDTO> getAllEvents() {
    // // 使用Mapper查询数据库或执行相应的操作
    // List<CalendarEventDTO> events = calendarEventMapper.findAll();
    // return events;
    // }

    public int findUserIdByEmail(String userEmail) {
        Integer userId = calendarEventMapper.findUserNoByEmail(userEmail);
        return userId != null ? userId : -1;
    }    
    
    public MembershipDTO savedMembership(MembershipDTO membershipDTO) {
        calendarEventMapper.saveMembership(membershipDTO);
        return membershipDTO;
    }

    public GroupDTO saveGroup(GroupDTO groupDTO) {
        calendarEventMapper.saveGroup(groupDTO);
        return groupDTO;
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

    // public List<CalendarEventDTO> getEventsByUser(int userno) {
    // // 使用Mapper查询数据库或执行相应的操作，以根据用户 ID 获取事件
    // List<CalendarEventDTO> events = calendarEventMapper.findByUser(userno);
    // return events;
    // }

    public List<String> getEventIdsByUser(int userno) {
        // 查询 "EVENT_SHARING" 表，获取用户参与的事件 ID 列表
        return calendarEventMapper.findEventIdsByUser(userno);
    }

    public List<String> getEventIdsByGroup(String groupId) {
        return calendarEventMapper.findEventIdsByGroup(groupId);
    }    

    public List<CalendarEventDTO> getEventsByIds(List<String> eventIds) {
        List<String> quotedEventIds = eventIds.stream()
                .map(id -> "'" + id + "'")
                .collect(Collectors.toList());

        String eventIdsString = String.join(",", quotedEventIds);
        System.out.println("eventIdsString: " + eventIdsString); // 打印eventIdsString的值
        List<CalendarEventDTO> events = calendarEventMapper.findByEventIds(eventIdsString);
        System.out.println("Number of events returned: " + events.size()); // 打印事件列表的大小
        return events;
    }

    public List<String> getGroupsIdsByUser(int userno) {
        return calendarEventMapper.findGroupIdsByUser(userno);
    }

    public List<GroupDTO> getGroupsByIds(List<String> groupIds) {
        List<String> quotedGroupIds = groupIds.stream()
                .map(id -> "'" + id + "'")
                .collect(Collectors.toList());

        String groupIdsString = String.join(",", quotedGroupIds);
        List<GroupDTO> events = calendarEventMapper.findByGroupIds(groupIdsString);
        System.out.println("Number of events returned: " + events.size()); // 打印事件列表的大小
        return events;
    }

    

    public void createEventSharing(EventSharingDTO eventSharingDTO) {
        calendarEventMapper.insertSharing(eventSharingDTO);
    }

    public void createGroupSharing(GroupSharingDTO groupSharingDTO) {
        calendarEventMapper.insertSharingGroup(groupSharingDTO);
    }

    public void deleteGroup(String groupId) {
        calendarEventMapper.deleteGroup(groupId);
    }









}
