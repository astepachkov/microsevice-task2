package com.mycrudapp.controller;

import com.mycrudapp.entity.User;
import com.mycrudapp.service.RoleService;
import com.mycrudapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/")
    public String showAllUsers(Model model, Authentication authentication) {
        List<User> users = userService.listUsers();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", authentication.getPrincipal());
        return "admin/allusers";
    }

    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin/new";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIds, Model model) {
        try {
            user.setRoles(roleService.getRolesByIds(roleIds));
            userService.addUser(user);
            return "redirect:/admin/";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Такой пользователь уже существует");
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "admin/new";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка добавления пользователя: " + e.getMessage());
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "admin/new";
        }
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin/edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIds) {
        user.setId(id);
        user.setRoles(roleService.getRolesByIds(roleIds));
        userService.updateUser(user);
        return "redirect:/admin/";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "Пользователь удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "При удалении пользователя произошла ошибка: " + e.getMessage());
        }
        return "redirect:/admin/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}
