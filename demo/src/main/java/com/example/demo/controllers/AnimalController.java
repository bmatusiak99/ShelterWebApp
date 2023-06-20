package com.example.demo.controllers;


import com.example.demo.models.Animal;
import com.example.demo.services.AnimalNotFoundException;
import com.example.demo.services.AnimalService;
import com.example.demo.services.PDFGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class AnimalController {

    @Autowired
    private AnimalService service;

    private final PDFGeneratorService pdfGeneratorService;

    public AnimalController(PDFGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }
//
//    @GetMapping("/animalsList")
//    public String showAnimalList(Model model){
//        List<Animal> listAnimals = service.listAll();
//        model.addAttribute("listAnimals", listAnimals);
//
//        return "animalsList";
//    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/addAnimal")
    public String showNewForm(Model model){
        model.addAttribute("animal", new Animal());
        model.addAttribute("pageTitle", "Add new animal");
        return "animalForm";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/saveAnimal")
    public String saveAnimal(Animal animal, RedirectAttributes ra) {
        service.save(animal);
        ra.addFlashAttribute("message", "The animal has been added successfully. ");

        return "redirect:/animalsList";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editAnimal/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try{
            Animal animal = service.get(id);
            model.addAttribute("animal", animal);
            model.addAttribute("pageTitle", "Edit animal (ID: " + id + ")");
            return "animalForm";
        }
        catch (AnimalNotFoundException e){
            ra.addFlashAttribute("message", "The animal has been edited successfully. ");
            return "redirect:/animalsList";
        }

    }

   @GetMapping("/generatePDF/{id}")
    public void generatePDF(HttpServletResponse response, @PathVariable("id") Integer id) throws IOException, AnimalNotFoundException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.pdfGeneratorService.export(response, id);
    }
















}
