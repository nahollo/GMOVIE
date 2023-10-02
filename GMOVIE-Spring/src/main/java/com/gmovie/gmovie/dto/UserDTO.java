package com.gmovie.gmovie.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDTO {

  private int no;
  private String name;
  private String email;
  private String pwd;
  private int gender;
  private int del;
  private LocalDateTime regDate;

}
