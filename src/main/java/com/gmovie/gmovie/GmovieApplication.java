package com.gmovie.gmovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class GmovieApplication {
	public static void main(String[] args) {
		SpringApplication.run(GmovieApplication.class, args);
	}
}