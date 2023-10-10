package com.gmovie.gmovie.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class MettingRoom {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/mettingRoom")
    public String mettingRoom(HttpSession session) {
        UUID uuid4 = UUID.randomUUID();
        String noHyphenUUID = uuid4.toString().replace("-", "");

        session.setAttribute("mettingRoomId", noHyphenUUID);
        session.setMaxInactiveInterval(30 * 60);

        jdbcTemplate.update("INSERT INTO SUMMARY (METTING_ID, NO) VALUES (?, ?)", noHyphenUUID, 1);

        return "METTING ROOM ID: " + noHyphenUUID;
    }
}