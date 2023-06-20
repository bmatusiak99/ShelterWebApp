package com.example.demo.controllers;


import com.example.demo.models.Animal;
import com.example.demo.models.Relation;
import com.example.demo.repositories.AnimalRepository;
import com.example.demo.repositories.ShelterRepository;
import com.example.demo.services.AnimalNotFoundException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class RelationController {

    private final AnimalRepository animalRepository;
    private final ShelterRepository shelterRepository;

    public RelationController(AnimalRepository animalRepository, ShelterRepository shelterRepository) {
        this.animalRepository = animalRepository;
        this.shelterRepository = shelterRepository;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/relation")
    public String addRelation(Model model){
        model.addAttribute("relation", new Relation());
        model.addAttribute("shelters", shelterRepository.findAll());
        model.addAttribute("animals", animalRepository.findAll());

        return "addRelation";
    }

    @Secured("ROLE_ADMIN")
    @Transactional
    @RequestMapping(value = "/addRelation", method = RequestMethod.POST)
    public String processForm(@ModelAttribute("relation") @Valid Relation relation, BindingResult result, ModelMap model)
    {
        if(result.hasErrors()){
            return "addRelation";
        }

        //building.getAnimal().setShelters(building.getShelter());
        //shelterRepository.save(building.getShelter());
        animalRepository.save(relation.getAnimal());

        System.out.println();
        return "redirect:/details";
    }


    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public String details(Model model) {
        model.addAttribute("List", animalRepository.findAll());
        return "details";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/deleteAnimal/{id}")
    public String deleteAnimal(@PathVariable("id") Long id, @Valid Relation relation, RedirectAttributes ra) {

//        try{
//            animalRepository.delete(relation.getAnimal());
//            ra.addFlashAttribute("message", "The animal ID " + id + " has been deleted");
//
//        }
//        catch (AnimalNotFoundException e){
//            ra.addFlashAttribute("message", e.getMessage());
//        }
//        return "redirect:/details";

        shelterRepository.delete(relation.getShelter());
        animalRepository.delete(relation.getAnimal());
        ra.addFlashAttribute("message", "The animal ID " + id + " has been deleted");
        return "redirect:/details";

    }


}
