package com.example.demo.services;


import com.example.demo.models.Animal;
import com.example.demo.repositories.AnimalRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PDFGeneratorService {

    @Autowired
    private AnimalRepository animalRepository;

    public List<Animal> listAll() {
        return(List<Animal>) animalRepository.findAll();
    }

    public void export(HttpServletResponse response, Integer id) throws IOException, AnimalNotFoundException {

        Optional<Animal> result = animalRepository.findById(id);

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA);
        fontHeader.setSize(9);

        Paragraph paragraph = new Paragraph("Animal Adoption Application Form", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph paragraph2 = new Paragraph("\n This questionnaire is designed to help us and yourself determine if a specific pet is the right one for you and your household. " +
                "All adopting parties are required to complete this form prior to adoption. Happy Paw Rescue reserves the right to deny any adoption based on answers to this questionnaire" +
                " and/or interviews prior to adpotions!", fontHeader);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph paragraph3 = new Paragraph("\n Animal information: ", fontTitle);
        paragraph3.setAlignment(Paragraph.ALIGN_LEFT);


        Paragraph paragraph4 = new Paragraph("Name: " + result.get().getAnimal_name() +
                "\n Species: " + result.get().getAnimal_species() +
                "\n Description: " + result.get().getAnimal_description() +
                "\n Age: " + result.get().getAnimal_age(), fontParagraph);
        paragraph4.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph paragraph5 = new Paragraph("\n Family & Housing: ", fontTitle);
        paragraph5.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph paragraph6 = new Paragraph("\n How many adults are there in your family (their relationship to you)? \n" +
                "_______________________________________________________________________ \n" +
                "How many children (ages)? \n" +
                "_______________________________________________________________________ \n" +
                "What type of home do you live in? (Single family, Town home, apartment, farm, etc?) \n" +
                "_______________________________________________________________________ \n" +
                "Please describe your household ___ Active  ___ Noisy  ___ Quiet ___ Average \n" +
                "Does anyone in the family have a known allergy to dogs? ____________________________ \n" +
                "Is everyone in agreement with the decision to adopt a dog? ____________________________ \n" +
                "Do you have time to provide adequate love and attention? ____________________________ \n", fontParagraph);



        document.add(paragraph);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(paragraph4);
        document.add(paragraph5);
        document.add(paragraph6);
        document.close();


    }
}
