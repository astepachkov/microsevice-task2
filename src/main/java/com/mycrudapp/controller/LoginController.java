package com.mycrudapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/")
    public String redirectToLoginPage(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/admin/";
        }
        return "redirect:/login";}

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}