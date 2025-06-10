package com.clinica.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/NomeBanco"; // Substitua NomeBanco pelo seu banco de dados
    private static final String USER = "root"; // Substitua pelo seu usuário do banco de dados
    private static final String PASSWORD = "sua_senha"; // Substitua pela sua senha do banco de dados

    /*private static final String URL = "jdbc:mysql://localhost:3306/clinica";
    private static final String USER = "root"; // Substitua pelo seu usuário do banco de dados
    private static final String PASSWORD = "zack22507"; // Substitua pela sua senha do banco de dados
    */

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado: " + e.getMessage());
            throw new SQLException("Erro ao carregar o driver JDBC", e);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
