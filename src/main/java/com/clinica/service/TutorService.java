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
        // Você pode adicionar lógica de validação aqui antes de salvar
        tutorDAO.create(tutor);
    }

    public void deleteTutor(int id) {
        tutorDAO.delete(id);
    }

    public List<Tutor> getAllTutors() {
        return tutorDAO.findAll();
    }
}
