package com.gmovie.gmovie.controller;

import org.springframework.web.bind.annotation.RestController;
import com.gmovie.gmovie.dto.ResultDTO;
import com.gmovie.gmovie.dto.UserDTO;
import com.gmovie.gmovie.mapper.UserMapper;
import com.gmovie.gmovie.service.UserService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "http://localhost:8801")
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

      @Autowired
      UserService uService;

      @PostMapping("/findAll")
      public ResultDTO findAll() {
            return uService.findAll();
      }

      @PostMapping("/editById")
      public ResultDTO editById(@RequestBody UserDTO uDto) {
            System.out.println(uDto);
            return uService.editById(uDto);
      }

      @DeleteMapping("/delete")
      public ResultDTO delete(@RequestParam("no") int no) {
            return uService.delete(no);
      }

      @PutMapping("/save")
      public ResultDTO save(@RequestBody UserDTO uDto) {
            return uService.save(uDto);
      }
}
