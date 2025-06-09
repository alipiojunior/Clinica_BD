package com.clinica.service;

import com.clinica.dao.ConsultaDAO;
import com.clinica.model.Consulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaDAO consultaDAO;

    @Autowired
    public ConsultaService(ConsultaDAO consultaDAO) {
        this.consultaDAO = consultaDAO;
    }

    public void createConsulta(Consulta consulta) {
        // Nenhuma validação de conflito aqui
        consultaDAO.create(consulta);
    }

    public void updateConsulta(Consulta consulta) {
        // Nenhuma validação de conflito aqui
        consultaDAO.update(consulta);
    }

    public void deleteConsulta(int id) {
        consultaDAO.delete(id);
    }

    public Consulta getConsultaById(int id) {
        return consultaDAO.findById(id);
    }

    public List<Consulta> getAllConsultas() {
        return consultaDAO.findAll();
    }
}
