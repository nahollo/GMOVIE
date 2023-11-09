package com.gmovie.gmovie.calendar;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CalendarEventMapper {

        // @Select("SELECT * FROM calendar_event WHERE userno = #{userno}")
        // List<CalendarEventDTO> findByUser(int userno);

        @Select("SELECT no FROM \"USER\" WHERE email = #{email}")
        Integer findUserNoByEmail(String email);

        @Insert("INSERT INTO \"GROUP_MEMBERSHIP\" (GROUP_ID, USER_ID) VALUES (#{id}, #{user})")
        void saveMembership(MembershipDTO membershipDTO);// Membership

        @Insert("INSERT INTO \"GROUP\" (id, name) VALUES (#{id}, #{name})")
        void saveGroup(GroupDTO groupDTO);// group

        @Select("SELECT GROUP_ID FROM GROUP_MEMBERSHIP WHERE USER_ID = #{userno}")
        List<String> findGroupIdsByUser(@Param("userno") int userno);

        @Select("SELECT * FROM \"GROUP\" WHERE id IN (${groupIds})")
        List<GroupDTO> findByGroupIds(@Param("groupIds") String groupIds);

        @Select("SELECT EVENT_ID FROM EVENT_SHARING WHERE USER_ID = #{userno}")
        List<String> findEventIdsByUser(@Param("userno") int userno);

        @Select("SELECT EVENT_ID FROM GROUP_SHARING WHERE GROUP_ID = #{groupId}")
        List<String> findEventIdsByGroup(@Param("groupId") String groupId);        

        @Select("SELECT * FROM calendar_event WHERE id IN (${eventIds})")
        List<CalendarEventDTO> findByEventIds(@Param("eventIds") String eventIds);

        // @Select("SELECT * FROM calendar_event")
        // List<CalendarEventDTO> findAll();

        @Insert("INSERT INTO calendar_event (id, title, day, month, year, time) " +
                        "VALUES (#{id}, #{title}, #{day}, #{month}, #{year}, #{time})")
        void insert(CalendarEventDTO event);

        @Insert("INSERT INTO event_sharing (event_id, user_id) VALUES (#{eventId}, #{userId})")
        void insertSharing(EventSharingDTO eventSharingDTO);

        @Insert("INSERT INTO GROUP_SHARING (event_id, group_id) VALUES (#{eventId}, #{groupId})")
        void insertSharingGroup(GroupSharingDTO groupSharingDTO);

        @Update("UPDATE calendar_event " +
                        "SET title = #{title}, day = #{day}, month = #{month}, year = #{year}, time = #{time} " +
                        "WHERE id = #{id}")
        void update(CalendarEventDTO event);

        @Delete("DELETE FROM calendar_event WHERE id = #{id}")
        void delete(String id);

        @Delete("DELETE FROM \"GROUP\" WHERE id = #{id}")
        void deleteGroup(String id);
        
}