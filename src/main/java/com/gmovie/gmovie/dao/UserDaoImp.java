package com.gmovie.gmovie.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gmovie.gmovie.dto.UserDTO;
import com.gmovie.gmovie.mapper.UserMapper;

@Repository
public class UserDaoImp implements UserDao {

  public List<UserDTO> findAll() {
    return null;
  }

  public int editById(UserDTO uDto) {
    return 0;
  }

  public int delete(int no) {
    return 0;
  }

  public int save(UserDTO uDto) {

    return 0;
  }

}
