package com.gmovie.gmovie.dao;

import java.util.List;

import com.gmovie.gmovie.dto.ResultDTO;
import com.gmovie.gmovie.dto.UserDTO;

public interface UserDao {

  public List<UserDTO> findAll();
  public int editById(UserDTO uDto);
  public int delete(int no);
  public ResultDTO save(UserDTO uDto);

}
