package com.clinica.controller;

import com.clinica.model.Tutor;
import com.clinica.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final TutorService tutorService;

    @Autowired
    public AdminController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @PostMapping("/tutors")
    public ResponseEntity<Tutor> createTutor(@RequestBody Tutor tutor) {
        tutorService.createTutor(tutor);
        return new ResponseEntity<>(tutor, HttpStatus.CREATED);
    }

    @DeleteMapping("/tutors/{id}")
    public ResponseEntity<Void> deleteTutor(@PathVariable int id) {
        tutorService.deleteTutor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Opcional: Adicionar um endpoint para listar tutores para Admin verificar
    @GetMapping("/tutors")
    public ResponseEntity<List<Tutor>> getAllTutors() {
        List<Tutor> tutors = tutorService.getAllTutors();
        return new ResponseEntity<>(tutors, HttpStatus.OK);
    }
}
