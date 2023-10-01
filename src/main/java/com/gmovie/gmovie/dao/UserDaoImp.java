package com.folder.app.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.folder.app.dto.UserDTO;
import com.folder.app.mapper.UserMapper;


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

  public int save(UserDTO uDto){
    
    return 0;
  }

}
