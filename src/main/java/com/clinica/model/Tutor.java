package com.clinica.model;

public class Tutor {
    private int idTutor;
    private String nome;
    private String cpf;
    private String email;

    // Construtor vazio
    public Tutor() {
    }

    // Construtor com todos os campos
    public Tutor(int idTutor, String nome, String cpf, String email) {
        this.idTutor = idTutor;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    // Getters e Setters
    public int getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(int idTutor) {
        this.idTutor = idTutor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "idTutor=" + idTutor +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
