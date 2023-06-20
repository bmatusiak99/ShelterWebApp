package com.example.demo.models;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "animalname", nullable = false)
    private String animal_name;

    @Column(name = "animalSpecies")
    private String animal_species;


    @Column(name = "animalFoundPlace")
    private String animalFoundPlace;

    @Column(name = "animaldescription", nullable = false)
    private String animal_description;

    @Column(name = "animalage", nullable = false)
    private Integer animal_age;

    @Column(name = "animalisadopted", nullable = false)
    private Boolean animal_isAdopted;

    @ManyToOne
    @JoinColumn(name = "shelters_id")
    private Shelter shelters;

    public Animal (){

    }

    public Animal(String animal_name, String animal_species, String animalFoundPlace, String animal_description, Integer animal_age, Boolean animal_isAdopted) {
        this.animal_name = animal_name;
        this.animal_species = animal_species;
        this.animalFoundPlace = animalFoundPlace;
        this.animal_description = animal_description;
        this.animal_age = animal_age;
        this.animal_isAdopted = animal_isAdopted;
    }
}
