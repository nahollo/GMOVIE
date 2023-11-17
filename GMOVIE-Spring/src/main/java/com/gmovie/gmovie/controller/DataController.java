package com.gmovie.gmovie.controller;

import org.springframework.web.bind.annotation.RestController;

import com.gmovie.gmovie.dto.ResultDTO;
import com.gmovie.gmovie.dto.UserDTO;
import com.gmovie.gmovie.mapper.UserMapper;
import com.gmovie.gmovie.service.UserService;


import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;




// @CrossOrigin(origins = "http://localhost:8800")
@RestController
public class DataController {

      @Autowired UserService uService;

      @PutMapping("/save")
      public ResultDTO save(@RequestBody UserDTO uDto){
            return uService.save(uDto);         
      }

      @Autowired
      private UserMapper userMapper;
      
      @PostMapping("/login")
      public ResponseEntity<?> login(@RequestBody UserDTO userDTO, HttpSession session) {
          // 在这里进行用户登录验证，查询数据库等操作
          UserDTO foundUser = userMapper.findByEmail(userDTO.getEmail());
      
          if (foundUser != null && foundUser.getPwd().equals(userDTO.getPwd())) {
              int userNo1 = foundUser.getNo(); // 用户编号
              session.setAttribute("userNo", userNo1);
              System.out.println(userNo1);
              // 验证成功，返回用户信息
              return ResponseEntity.ok(foundUser);
          } else {
              // 验证失败，返回错误消息
              return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码不正确");
          }
      }
      

      
}
