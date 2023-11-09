package com.gmovie.gmovie.controller;

import org.springframework.web.bind.annotation.RestController;

import com.gmovie.gmovie.dto.ResultDTO;
import com.gmovie.gmovie.dto.UserDTO;
import com.gmovie.gmovie.mapper.UserMapper;
import com.gmovie.gmovie.service.UserService;


import jakarta.annotation.PostConstruct;

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
      
      @GetMapping("/")
      public String home() {
          return "data 준비 중 ....";
      }

      @GetMapping("/api")
      public String api() {
          return "api data 준비 중 ....";
      }

      @Autowired UserService uService;
      
      @PostMapping("/findAll")
      public ResultDTO findAll(){
            return uService.findAll();
      }
      @PostMapping("/editById")
      public ResultDTO editById(@RequestBody UserDTO uDto){
            System.out.println(uDto);
            return uService.editById(uDto);         
      }
      @DeleteMapping("/delete")
      public ResultDTO delete(@RequestParam("no") int no){
            return uService.delete(no);         
      }
      @PutMapping("/save")
      public ResultDTO save(@RequestBody UserDTO uDto){
            return uService.save(uDto);         
      }

      @Autowired
      private UserMapper userMapper;
      
      @PostMapping("/login")
      public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
          // 在这里进行用户登录验证，查询数据库等操作
          UserDTO foundUser = userMapper.findByEmail(userDTO.getEmail());
      
          if (foundUser != null && foundUser.getPwd().equals(userDTO.getPwd())) {
              // 验证成功，返回用户信息
              return ResponseEntity.ok(foundUser);
          } else {
              // 验证失败，返回错误消息
              return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码不正确");
          }
      }
      

      
}
