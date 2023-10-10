package com.gmovie.gmovie.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.gmovie.gmovie.dto.UserDTO;


@Mapper
public interface UserMapper {
      
  @Select("SELECT * FROM user3")
  public List<UserDTO> findAll();


  @Update("update user3 set name = #{name}, pwd = #{pwd}, gender = #{gender} where no = #{no}")
  public int editById(UserDTO uDto);

  @Update("update user3 set del = 1 where no = #{no}")
  public int delete(int no);

  
  @Insert("insert into user3 (name, email, pwd, gender) values (#{name},#{email},#{pwd},#{gender})")
  public int save(UserDTO uDto);

  @Select("SELECT * FROM user3 WHERE email = #{email}")
  public UserDTO findByEmail(String email);
  

}
