// src/main/java/com/clinica/service/TutorService.java
package com.clinica.service;

import com.clinica.dao.TutorDAO;
import com.clinica.model.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {

    private final TutorDAO tutorDAO;

    @Autowired
    public TutorService(TutorDAO tutorDAO) {
        this.tutorDAO = tutorDAO;
    }

    public void createTutor(Tutor tutor) {
        tutorDAO.create(tutor);
    }

    public void deleteTutor(int id) {
        tutorDAO.delete(id);
    }

    // NOVO MÉTODO: Obter Tutor por ID
    public Tutor getTutorById(int id) {
        return tutorDAO.findById(id);
    }

    public List<Tutor> getAllTutors() {
        return tutorDAO.findAll();
    }
}