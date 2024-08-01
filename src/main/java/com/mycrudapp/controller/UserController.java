package com.mycrudapp.controller;

import com.mycrudapp.entity.User;
import com.mycrudapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        model.addAttribute("user", user);
        return "userProfile";
    }

    @GetMapping("/edit")
    public String editUserProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        model.addAttribute("user", user);
        return "userEdit";
    }

    @PostMapping("/update")
    public String updateUserProfile(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(createNewAuth(auth, user));
        return "redirect:/user/profile";
    }

    private Authentication createNewAuth(Authentication currentAuth, User updatedUser) {
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(currentAuth.getAuthorities());
        return new UsernamePasswordAuthenticationToken(updatedUser, currentAuth.getCredentials(), updatedAuthorities);
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }

}