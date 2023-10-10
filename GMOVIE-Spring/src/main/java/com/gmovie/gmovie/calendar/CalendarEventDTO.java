package com.gmovie.gmovie.calendar;

import lombok.Data;

@Data
public class CalendarEventDTO {
      private String id; // 事件的唯一标识
      private String title; // 事件标题
      private Integer day; // 事件的日期中的天
      private Integer month; // 事件的日期中的月（請注意，這裡的月份是1到12，而不是0到11）
      private Integer year; // 事件的日期中的年份
      private String time; // 事件的時間範圍

}
