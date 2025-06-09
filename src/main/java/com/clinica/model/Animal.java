package com.clinica.model;

public class Animal {
    private int idAnimal;
    private int idTutor; // Chave estrangeira
    private String nome;
    private String especie;
    private String raca;

    // Construtor vazio
    public Animal() {
    }

    // Construtor com todos os campos
    public Animal(int idAnimal, int idTutor, String nome, String especie, String raca) {
        this.idAnimal = idAnimal;
        this.idTutor = idTutor;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
    }

    // Getters e Setters
    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

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

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "idAnimal=" + idAnimal +
                ", idTutor=" + idTutor +
                ", nome='" + nome + '\'' +
                ", especie='" + especie + '\'' +
                ", raca='" + raca + '\'' +
                '}';
    }
}
