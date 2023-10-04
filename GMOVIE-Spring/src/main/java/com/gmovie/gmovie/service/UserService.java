package com.gmovie.gmovie.service;

import com.gmovie.gmovie.dto.ResultDTO;
import com.gmovie.gmovie.dto.UserDTO;

public interface UserService {

  public ResultDTO findAll();
  public ResultDTO editById(UserDTO uDto);
  public ResultDTO delete(int no);
  public ResultDTO save(UserDTO uDto);

}
