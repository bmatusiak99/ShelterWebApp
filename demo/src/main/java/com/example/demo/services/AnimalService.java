package com.example.demo.services;

import com.example.demo.models.Animal;
import com.example.demo.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public List<Animal> listAll() {
        return(List<Animal>) animalRepository.findAll();
    }

    public void save(Animal animal) {
        animalRepository.save(animal);
    }
    public Animal get(Integer id) throws AnimalNotFoundException {
        Optional<Animal> result = animalRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new AnimalNotFoundException("Could not find any animals with the given ID");
    }

    public void delete(Integer id) throws AnimalNotFoundException {

        animalRepository.deleteById(id);
    }

}
