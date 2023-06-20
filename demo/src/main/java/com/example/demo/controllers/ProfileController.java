package com.example.demo.controllers;

import com.example.demo.models.Profile;
import com.example.demo.models.User;
import com.example.demo.repositories.ProfileRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository;

    //private Profile profile = new Profile();

    @GetMapping("/profileForm")
    public String registration(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userRepository.findByUsername(name);
        var a = profileRepository.findByUser(user);
        if (a != null){
            model.addAttribute("userCommand", a);
        }
        else {
            model.addAttribute("userCommand", new Profile());
        }
        return "profileForm";
    }


    @GetMapping("/profile")
    public String profile(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userRepository.findByUsername(name);
        var a = profileRepository.findByUser(user);
        if (a != null){
            model.addAttribute("profile", a);
        }
        else{
            model.addAttribute("profile", new Profile());
        }

        model.addAttribute("user", userRepository.findByUsername(name));
        return "profile";
    }

    @Transactional
    @PostMapping("/profileRegistration")
    public String profileRegistration(@Valid @ModelAttribute("userCommand")
                                              Profile profile, Model model, Errors error, Optional<Long> id) {
        if(error.hasErrors()) {
            return "profileForm";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            profile.setUser(userRepository.findByUsername(name));
        profileRepository.save(profile);
        return "redirect:/profile";
    }

}
