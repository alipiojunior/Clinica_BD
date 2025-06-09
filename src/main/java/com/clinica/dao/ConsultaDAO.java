package com.clinica.dao;

import com.clinica.config.DatabaseConnection;
import com.clinica.model.Consulta;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate; // Revertido para LocalDate
import java.util.ArrayList;
import java.util.List;

@Repository
public class ConsultaDAO {

    public void create(Consulta consulta) {
        String sql = "INSERT INTO consulta (id_animal, data_hora, veterinario, diagnostico) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, consulta.getIdAnimal());
            stmt.setDate(2, Date.valueOf(consulta.getDataHora())); // Usa Date.valueOf para LocalDate
            stmt.setString(3, consulta.getVeterinario());
            stmt.setString(4, consulta.getDiagnostico());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    consulta.setIdConsulta(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar consulta: " + e.getMessage());
            throw new RuntimeException("Erro ao criar consulta", e);
        }
    }

    public void update(Consulta consulta) {
        String sql = "UPDATE consulta SET id_animal = ?, data_hora = ?, veterinario = ?, diagnostico = ? WHERE id_consulta = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, consulta.getIdAnimal());
            stmt.setDate(2, Date.valueOf(consulta.getDataHora())); // Usa Date.valueOf
            stmt.setString(3, consulta.getVeterinario());
            stmt.setString(4, consulta.getDiagnostico());
            stmt.setInt(5, consulta.getIdConsulta());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar consulta: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar consulta", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM consulta WHERE id_consulta = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar consulta: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar consulta", e);
        }
    }

    public Consulta findById(int id) {
        String sql = "SELECT id_consulta, id_animal, data_hora, veterinario, diagnostico FROM consulta WHERE id_consulta = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Consulta consulta = new Consulta();
                    consulta.setIdConsulta(rs.getInt("id_consulta"));
                    consulta.setIdAnimal(rs.getInt("id_animal"));
                    consulta.setDataHora(rs.getDate("data_hora").toLocalDate()); // Usa getDate e toLocalDate
                    consulta.setVeterinario(rs.getString("veterinario"));
                    consulta.setDiagnostico(rs.getString("diagnostico"));
                    return consulta;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar consulta por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar consulta por ID", e);
        }
        return null;
    }

    public List<Consulta> findAll() {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT id_consulta, id_animal, data_hora, veterinario, diagnostico FROM consulta";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setIdConsulta(rs.getInt("id_consulta"));
                consulta.setIdAnimal(rs.getInt("id_animal"));
                consulta.setDataHora(rs.getDate("data_hora").toLocalDate()); // Usa getDate e toLocalDate
                consulta.setVeterinario(rs.getString("veterinario"));
                consulta.setDiagnostico(rs.getString("diagnostico"));
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as consultas: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar todas as consultas", e);
        }
        return consultas;
    }
    // O método existsConflictingConsultas NÃO EXISTE NESTA VERSÃO
}