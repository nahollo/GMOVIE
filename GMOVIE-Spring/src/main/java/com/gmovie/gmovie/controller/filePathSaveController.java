package com.gmovie.gmovie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class filePathSaveController {
    @GetMapping("/filePathSave")
    public String filePathSave() {
        return "filePathSave";
    }
}
