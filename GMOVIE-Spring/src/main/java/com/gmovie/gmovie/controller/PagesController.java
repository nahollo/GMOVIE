package com.gmovie.gmovie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/calendar")
    public String showCalendarPage() {
    return "calendar"; 
    }


    @GetMapping("/home")
    public String showHomePage() {
    return "home"; 
    }

    @GetMapping("/login")
    public String showLoginPage() {
    return "login"; 
    }

    @GetMapping("/signup")
    public String showSignUpPage() {
    return "signup"; 
    }

    @GetMapping("/aboutus")
    public String showAboutUsPage() {
    return "aboutus"; 
    }

 

    @GetMapping("/service")
    public String showServicesPage() {
    return "service"; 
    }

    
    
    @GetMapping("/developer")
    public String showDeveloperPage() {
    return "developer"; 
    }

    @GetMapping("/summary")
    public String showSummaryPage() {
    return "summary"; 
    }







    
      
}
