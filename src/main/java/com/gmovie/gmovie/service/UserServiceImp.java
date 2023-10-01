package com.gmovie.gmovie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmovie.gmovie.dao.UserDao;
import com.gmovie.gmovie.domain.User;
import com.gmovie.gmovie.dto.ResultDTO;
import com.gmovie.gmovie.dto.UserDTO;

@Service
public class UserServiceImp implements UserService {

      private ResultDTO rDto;

      @Autowired
      UserDao uDao;

      public ResultDTO findAll() {
            rDto = new ResultDTO();
            List<UserDTO> resultList = uDao.findAll();
            if (resultList != null) {
                  rDto.setState(true);
                  rDto.setResult(resultList);
            } else {
                  rDto.setState(false);
            }
            return rDto;
      }

      public ResultDTO editById(UserDTO uDto) {

            return rDto;
      }

      public ResultDTO delete(int no) {

            return rDto;
      }

      public ResultDTO save(UserDTO uDto) {

            return rDto;
      }

      @Override
      public Object saveUser(User user) {
            throw new UnsupportedOperationException("Unimplemented method 'saveUser'");
      }

}
