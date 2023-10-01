package com.folder.app.controller;

import org.springframework.web.bind.annotation.RestController;

import com.folder.app.dto.ResultDTO;
import com.folder.app.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@CrossOrigin(origins = "http://localhost:8800")
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
      public ResultDTO editById(){
            return null;         
      }
      @PostMapping("/delete")
      public ResultDTO delete(){
            return null;         
      }
      @PostMapping("/save")
      public ResultDTO save(){
            return null;         
      }
      
}
