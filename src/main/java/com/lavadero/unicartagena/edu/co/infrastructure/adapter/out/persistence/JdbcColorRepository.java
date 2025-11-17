package com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.Color;
import com.lavadero.unicartagena.edu.co.domain.port.out.vehiculo.ColorRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC del repositorio de colores.
 */
public class JdbcColorRepository implements ColorRepository {

    @Override
    public Color guardar(Color color) {
        if (color.getId() == null) {
            return insertar(color);
        } else {
            return actualizar(color);
        }
    }

    private Color insertar(Color color) {
        String sql = "INSERT INTO colores (color, hexcode) VALUES (?, ?) RETURNING id";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, color.getColor());
            stmt.setString(2, color.getHexcode());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                color.setId(rs.getInt("id"));
            }
            
            return color;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar color: " + e.getMessage(), e);
        }
    }

    private Color actualizar(Color color) {
        String sql = "UPDATE colores SET color = ?, hexcode = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, color.getColor());
            stmt.setString(2, color.getHexcode());
            stmt.setInt(3, color.getId());
            
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("No se encontró el color con ID " + color.getId());
            }
            
            return color;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar color: " + e.getMessage(), e);
        }
    }

    @Override
    public Color buscarPorId(Integer id) {
        String sql = "SELECT id, color, hexcode FROM colores WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearColor(rs);
            }
            
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar color por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public Color buscarPorNombre(String nombre) {
        String sql = "SELECT id, color, hexcode FROM colores WHERE UPPER(color) = UPPER(?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearColor(rs);
            }
            
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar color por nombre: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Color> listarTodos() {
        String sql = "SELECT id, color, hexcode FROM colores ORDER BY color";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            List<Color> colores = new ArrayList<Color>();
            while (rs.next()) {
                colores.add(mapearColor(rs));
            }
            
            return colores;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar colores: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM colores WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar color: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existePorNombre(String nombre) {
        String sql = "SELECT COUNT(*) FROM colores WHERE UPPER(color) = UPPER(?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de color: " + e.getMessage(), e);
        }
    }

    /**
     * Mapea un ResultSet a un objeto Color.
     */
    private Color mapearColor(ResultSet rs) throws SQLException {
        Color color = new Color();
        color.setId(rs.getInt("id"));
        color.setColor(rs.getString("color"));
        color.setHexcode(rs.getString("hexcode"));
        return color;
    }
}
