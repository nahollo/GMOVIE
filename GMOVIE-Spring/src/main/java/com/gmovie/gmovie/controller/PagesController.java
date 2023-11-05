package com.gmovie.gmovie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/calendar")
    public String showCalendarPage() {
    return "calendar"; 
    }

    @GetMapping("/chat")
    public String showChatPage() {
    return "chat"; 
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

    @GetMapping("/experts")
    public String showExpertsPage() {
    return "experts"; 
    }

    @GetMapping("/services")
    public String showServicesPage() {
    return "services"; 
    }

    @GetMapping("/newsmain")
    public String showNewsMainPage() {
    return "newsmain"; 
    }

    @GetMapping("/contactus")
    public String showContactUsPage() {
    return "contactus"; 
    }

    @GetMapping("/summary")
    public String showSummaryPage() {
    return "summary"; 
    }


    
      
}
