package com.example.demo.controllers;


import com.example.demo.models.Shelter;
import com.example.demo.repositories.ShelterRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ShelterController {

    private final ShelterRepository shelterRepository;

    public ShelterController(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @RequestMapping(value = "/addShelter", method = RequestMethod.POST)
    public String processForm(@ModelAttribute("shelter") @Valid Shelter shelter, BindingResult result){
        if(result.hasErrors()){
            return "addShelter";
        }
        shelterRepository.save(shelter);
        System.out.println();
        return "redirect:/show_shelters";
    }

    @GetMapping({"/shelter", "/editShelter"})
    public String editRegistration(Model model, Shelter shelter,
                                   @RequestParam
                                           (value="id", required=false, defaultValue="-1") Long id) {
        if (id.equals(-1l)) {
            model.addAttribute("shelter", new Shelter());
        } else {
            model.addAttribute("shelter", shelterRepository.findById(id));
        }
        return "addShelter";
    }

    @RequestMapping(value = "/show_shelters", method = RequestMethod.GET)
    public String details(Model model) {
        model.addAttribute("List", shelterRepository.findAll());
        return "sheltersList";
    }

}
