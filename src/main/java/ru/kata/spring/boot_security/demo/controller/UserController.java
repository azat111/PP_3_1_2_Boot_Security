package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getCars(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("users", userService.index());
        model.addAttribute("user", user);
        return "admin";
    }

    @GetMapping("/user")
    public String getUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin/user/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "user/addUser";
    }

    @PostMapping("/admin/user/addUser")
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/deleteUser")
    public String deleteUser() {
        return "user/deleteUser";
    }

    @PostMapping("/admin/user/deleteUser")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/editUser")
    public String editUser() {
        return "user/editUser";
    }

    @PostMapping("/admin/user/editUser")
    public String edit(@RequestParam("id") int id, String name, int age, String password, String roles) {
        userService.update(id, name, age, password, roles);
        return "redirect:/admin";
    }
}
