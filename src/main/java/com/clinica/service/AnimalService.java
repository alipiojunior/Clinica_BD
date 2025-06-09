package com.clinica.service;

import com.clinica.dao.AnimalDAO;
import com.clinica.model.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalDAO animalDAO;

    @Autowired
    public AnimalService(AnimalDAO animalDAO) {
        this.animalDAO = animalDAO;
    }

    public void createAnimal(Animal animal) {
        animalDAO.create(animal);
    }

    public void deleteAnimal(int id) {
        animalDAO.delete(id);
    }

    public Animal getAnimalById(int id) {
        return animalDAO.findById(id);
    }

    public List<Animal> getAllAnimals() {
        return animalDAO.findAll();
    }
}
