package com.clinica.dao;

import com.clinica.config.DatabaseConnection;
import com.clinica.model.Tutor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TutorDAO {

    public void create(Tutor tutor) {
        String sql = "INSERT INTO tutor (nome, cpf, email) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getCpf());
            stmt.setString(3, tutor.getEmail());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tutor.setIdTutor(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar tutor: " + e.getMessage());
            throw new RuntimeException("Erro ao criar tutor", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tutor WHERE id_tutor = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar tutor: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar tutor", e);
        }
    }

    public List<Tutor> findAll() {
        List<Tutor> tutors = new ArrayList<>();
        String sql = "SELECT id_tutor, nome, cpf, email FROM tutor";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Tutor tutor = new Tutor();
                tutor.setIdTutor(rs.getInt("id_tutor"));
                tutor.setNome(rs.getString("nome"));
                tutor.setCpf(rs.getString("cpf"));
                tutor.setEmail(rs.getString("email"));
                tutors.add(tutor);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os tutores: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar todos os tutores", e);
        }
        return tutors;
    }
}