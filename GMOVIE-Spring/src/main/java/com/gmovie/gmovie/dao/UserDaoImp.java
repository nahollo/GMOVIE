package com.gmovie.gmovie.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gmovie.gmovie.dto.ResultDTO;
import com.gmovie.gmovie.dto.UserDTO;
import com.gmovie.gmovie.mapper.UserMapper;

@Repository
public class UserDaoImp implements UserDao {

  @Autowired
  UserMapper uMapper;

  @Override
  public List<UserDTO> findAll() {
    return uMapper.findAll();
  }

  @Override
  public int editById(UserDTO uDto) {
    return uMapper.editById(uDto);
  }

  @Override
  public int delete(int no) {
    return uMapper.delete(no);
  }

  @Override
  public ResultDTO save(UserDTO uDto) {
    ResultDTO result = new ResultDTO();
    int state = uMapper.save(uDto);
    if (state == 1) {
      System.out.println(uDto);
      result.setState(true);
      result.setResult(uDto);
    } else {
      result.setState(false);
    }
    return result;
  }

}
