package com.clinica.controller;

import com.clinica.model.Animal;
import com.clinica.model.Consulta;
import com.clinica.service.AnimalService;
import com.clinica.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final AnimalService animalService;
    private final ConsultaService consultaService;

    @Autowired
    public UserController(AnimalService animalService, ConsultaService consultaService) {
        this.animalService = animalService;
        this.consultaService = consultaService;
    }

    // --- Endpoints para Consulta ---
    @PostMapping("/consultas")
    public ResponseEntity<Consulta> createConsulta(@RequestBody Consulta consulta) { // Retorno ResponseEntity<Consulta>
        consultaService.createConsulta(consulta);
        return new ResponseEntity<>(consulta, HttpStatus.CREATED);
    }

    @PutMapping("/consultas/{id}")
    public ResponseEntity<Consulta> updateConsulta(@PathVariable int id, @RequestBody Consulta consulta) { // Retorno ResponseEntity<Consulta>
        consulta.setIdConsulta(id); // Garante que o ID do caminho seja usado
        consultaService.updateConsulta(consulta);
        return new ResponseEntity<>(consulta, HttpStatus.OK);
    }

    @DeleteMapping("/consultas/{id}")
    public ResponseEntity<Void> deleteConsulta(@PathVariable int id) {
        consultaService.deleteConsulta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/consultas/{id}")
    public ResponseEntity<Consulta> getConsultaById(@PathVariable int id) {
        Consulta consulta = consultaService.getConsultaById(id);
        if (consulta != null) {
            return new ResponseEntity<>(consulta, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/consultas")
    public ResponseEntity<List<Consulta>> getAllConsultas() {
        List<Consulta> consultas = consultaService.getAllConsultas();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    // --- Endpoints para Animal ---
    @PostMapping("/animals")
    public ResponseEntity<Animal> createAnimal(@RequestBody Animal animal) {
        animalService.createAnimal(animal);
        return new ResponseEntity<>(animal, HttpStatus.CREATED);
    }

    @DeleteMapping("/animals/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable int id) {
        animalService.deleteAnimal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/animals/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable int id) {
        Animal animal = animalService.getAnimalById(id);
        if (animal != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/animals")
    public ResponseEntity<List<Animal>> getAllAnimals() {
        List<Animal> animals = animalService.getAllAnimals();
        return new ResponseEntity<>(animals, HttpStatus.OK);
    }
}
