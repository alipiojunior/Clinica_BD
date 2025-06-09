package com.clinica.model;

import java.time.LocalDate; // Revertido para LocalDate

public class Consulta {
    private int idConsulta;
    private int idAnimal;
    private LocalDate dataHora; // Revertido para LocalDate
    private String veterinario;
    private String diagnostico;

    // Construtor vazio
    public Consulta() {
    }

    // Construtor com todos os campos
    public Consulta(int idConsulta, int idAnimal, LocalDate dataHora, String veterinario, String diagnostico) {
        this.idConsulta = idConsulta;
        this.idAnimal = idAnimal;
        this.dataHora = dataHora;
        this.veterinario = veterinario;
        this.diagnostico = diagnostico;
    }

    // Getters e Setters
    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public LocalDate getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDate dataHora) {
        this.dataHora = dataHora;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "idConsulta=" + idConsulta +
                ", idAnimal=" + idAnimal +
                ", dataHora=" + dataHora +
                ", veterinario='" + veterinario + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                '}';
    }
}