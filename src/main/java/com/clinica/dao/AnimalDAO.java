package com.clinica.dao;


import com.clinica.config.DatabaseConnection;
import com.clinica.model.Animal;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnimalDAO {

    public void create(Animal animal) {
        String sql = "INSERT INTO animal (id_tutor, nome, especie, raça) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, animal.getIdTutor());
            stmt.setString(2, animal.getNome());
            stmt.setString(3, animal.getEspecie());
            stmt.setString(4, animal.getRaca());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    animal.setIdAnimal(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar animal: " + e.getMessage());
            throw new RuntimeException("Erro ao criar animal", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM animal WHERE id_animal = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar animal: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar animal", e);
        }
    }

    public Animal findById(int id) {
        String sql = "SELECT id_animal, id_tutor, nome, especie, raça FROM animal WHERE id_animal = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Animal animal = new Animal();
                    animal.setIdAnimal(rs.getInt("id_animal"));
                    animal.setIdTutor(rs.getInt("id_tutor"));
                    animal.setNome(rs.getString("nome"));
                    animal.setEspecie(rs.getString("especie"));
                    animal.setRaca(rs.getString("raça"));
                    return animal;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar animal por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar animal por ID", e);
        }
        return null;
    }

    public List<Animal> findAll() {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT id_animal, id_tutor, nome, especie, raça FROM animal";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Animal animal = new Animal();
                animal.setIdAnimal(rs.getInt("id_animal"));
                animal.setIdTutor(rs.getInt("id_tutor"));
                animal.setNome(rs.getString("nome"));
                animal.setEspecie(rs.getString("especie"));
                animal.setRaca(rs.getString("raça"));
                animals.add(animal);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os animais: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar todos os animais", e);
        }
        return animals;
    }
}