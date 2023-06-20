package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter @Setter

public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String streetName;
    private String buildingNumber;

    @OneToMany(mappedBy = "shelters")
    private Set<Animal> animals;

    public Shelter() {

    }

    public Shelter(Long id, String city, String streetName, String buildingNumber, Set<Animal> animals) {
        this.id = id;
        this.city = city;
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.animals = animals;
    }
}
