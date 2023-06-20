package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping("")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showReg(User user, Model model){
        new User();

        model.addAttribute("regUser", user);
        return "register";

    }

    @PostMapping("/register")
    public String register(@ModelAttribute("regUser") User v) {
        userService.saveUser(v);
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String loginGet() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(){
        return "redirect:/";
    }



}
