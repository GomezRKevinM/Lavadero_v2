package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.cli;

import com.lavadero.unicartagena.edu.co.infrastructure.config.DatabaseConfig;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class MigrarPasswordsApp {

    public static void main(String[] args) {
        System.out.println("=== MIGRACIÓN DE CONTRASEÑAS A SHA-256 ===\n");

        try (Connection conn = DatabaseConfig.getConnection()) {
            String selectSql = "SELECT id, username, password, answer_security FROM usuarios";
            String updateSql = "UPDATE usuarios SET password = ?, answer_security = ? WHERE id = ?";

            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            ResultSet rs = selectStmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String passwordActual = rs.getString("password");
                String answerActual = rs.getString("answer_security");

                if (passwordActual != null && passwordActual.length() < 44) {
                    String passwordHash = encriptarPassword(passwordActual);
                    String answerHash = (answerActual != null && !answerActual.isEmpty()) 
                        ? encriptarPassword(answerActual) 
                        : answerActual;

                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setString(1, passwordHash);
                    updateStmt.setString(2, answerHash);
                    updateStmt.setLong(3, id);
                    updateStmt.executeUpdate();

                    System.out.println("✓ Usuario migrado: " + username + " (ID: " + id + ")");
                    count++;
                } else {
                    System.out.println("⊘ Usuario ya hasheado: " + username + " (ID: " + id + ")");
                }
            }

            System.out.println("\n=== MIGRACIÓN COMPLETADA ===");
            System.out.println("Total de usuarios migrados: " + count);

        } catch (SQLException e) {
            System.err.println("Error al migrar contraseñas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String encriptarPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar contraseña", e);
        }
    }
}
