package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Relation {

    private Animal animal;
    private Shelter shelter;

    public Relation(Animal animal, Shelter shelter) {
        this.animal = animal;
        this.shelter = shelter;
    }

    public Relation(Animal animal) {
        this.animal = animal;
    }

    public Relation(Shelter shelter) {
        this.shelter = shelter;
    }

    public Relation() {

    }
}
