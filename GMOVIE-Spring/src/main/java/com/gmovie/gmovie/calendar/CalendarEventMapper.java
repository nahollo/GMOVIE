package com.gmovie.gmovie.calendar;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CalendarEventMapper {

    @Select("SELECT * FROM calendar_event WHERE id = #{id}")
    CalendarEventDTO findById(String id);

    @Select("SELECT * FROM calendar_event")
    List<CalendarEventDTO> findAll();

    @Insert("INSERT INTO calendar_event (id, title, day, month, year, time) " +
            "VALUES (#{id}, #{title}, #{day}, #{month}, #{year}, #{time})")
    void insert(CalendarEventDTO event);

    @Update("UPDATE calendar_event " +
            "SET title = #{title}, day = #{day}, month = #{month}, year = #{year}, time = #{time} " +
            "WHERE id = #{id}")
    void update(CalendarEventDTO event);

    @Delete("DELETE FROM calendar_event WHERE id = #{id}")
    void delete(String id);
}