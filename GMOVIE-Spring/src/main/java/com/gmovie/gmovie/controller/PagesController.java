package com.gmovie.gmovie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
public class PagesController {

    @GetMapping("/calendar")
    public String showCalendarPage() {
        return "calendar";
    }

    
    @GetMapping("/home")
    public String showHomePage2() {
        return "home";
    }
    @GetMapping("/")
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

    // Controller
    @GetMapping("/summary")
    public String showSummaryPage(@RequestParam(name = "text", required = false) String text, Model model) {
        if (text != null) {
            model.addAttribute("text", text);
        }
        return "summary";
    }

    @GetMapping("/logout")
    public String showLoginPage(HttpServletRequest request, HttpSession session, Model model) {
        session.invalidate();
        return "logout";
    }
}
